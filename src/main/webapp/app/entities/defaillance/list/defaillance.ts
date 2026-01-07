import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { FormatMediumDatePipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { IDefaillance } from '../defaillance.model';
import { DefaillanceDeleteDialog } from '../delete/defaillance-delete-dialog';
import { DefaillanceService, EntityArrayResponseType } from '../service/defaillance.service';

@Component({
  selector: 'jhi-defaillance',
  templateUrl: './defaillance.html',
  imports: [RouterLink, FormsModule, SharedModule, SortDirective, SortByDirective, FormatMediumDatePipe],
})
export class Defaillance implements OnInit {
  subscription: Subscription | null = null;
  defaillances = signal<IDefaillance[]>([]);
  isLoading = false;

  sortState = sortStateSignal({});

  readonly router = inject(Router);
  protected readonly defaillanceService = inject(DefaillanceService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);

  trackId = (item: IDefaillance): number => this.defaillanceService.getDefaillanceIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.defaillances().length === 0) {
            this.load();
          } else {
            this.defaillances.set(this.refineData(this.defaillances()));
          }
        }),
      )
      .subscribe();
  }

  delete(defaillance: IDefaillance): void {
    const modalRef = this.modalService.open(DefaillanceDeleteDialog, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.defaillance = defaillance;
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
    this.defaillances.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IDefaillance[]): IDefaillance[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IDefaillance[] | null): IDefaillance[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.defaillanceService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
