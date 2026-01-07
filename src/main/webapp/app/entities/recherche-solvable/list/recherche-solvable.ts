import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { RechercheSolvableDeleteDialog } from '../delete/recherche-solvable-delete-dialog';
import { IRechercheSolvable } from '../recherche-solvable.model';
import { EntityArrayResponseType, RechercheSolvableService } from '../service/recherche-solvable.service';

@Component({
  selector: 'jhi-recherche-solvable',
  templateUrl: './recherche-solvable.html',
  imports: [RouterLink, FormsModule, SharedModule, SortDirective, SortByDirective],
})
export class RechercheSolvable implements OnInit {
  subscription: Subscription | null = null;
  rechercheSolvables = signal<IRechercheSolvable[]>([]);
  isLoading = false;

  sortState = sortStateSignal({});

  readonly router = inject(Router);
  protected readonly rechercheSolvableService = inject(RechercheSolvableService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);

  trackId = (item: IRechercheSolvable): number => this.rechercheSolvableService.getRechercheSolvableIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.rechercheSolvables().length === 0) {
            this.load();
          } else {
            this.rechercheSolvables.set(this.refineData(this.rechercheSolvables()));
          }
        }),
      )
      .subscribe();
  }

  delete(rechercheSolvable: IRechercheSolvable): void {
    const modalRef = this.modalService.open(RechercheSolvableDeleteDialog, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rechercheSolvable = rechercheSolvable;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe((res: EntityArrayResponseType) => this.onResponseSuccess(res));
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.rechercheSolvables.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IRechercheSolvable[]): IRechercheSolvable[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IRechercheSolvable[] | null): IRechercheSolvable[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.rechercheSolvableService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }
}
