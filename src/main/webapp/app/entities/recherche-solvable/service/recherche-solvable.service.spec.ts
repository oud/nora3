import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IRechercheSolvable } from '../recherche-solvable.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../recherche-solvable.test-samples';

import { RechercheSolvableService } from './recherche-solvable.service';

const requireRestSample: IRechercheSolvable = {
  ...sampleWithRequiredData,
};

describe('RechercheSolvable Service', () => {
  let service: RechercheSolvableService;
  let httpMock: HttpTestingController;
  let expectedResult: IRechercheSolvable | IRechercheSolvable[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RechercheSolvableService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RechercheSolvable', () => {
      const rechercheSolvable = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(rechercheSolvable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RechercheSolvable', () => {
      const rechercheSolvable = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(rechercheSolvable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RechercheSolvable', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RechercheSolvable', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RechercheSolvable', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRechercheSolvableToCollectionIfMissing', () => {
      it('should add a RechercheSolvable to an empty array', () => {
        const rechercheSolvable: IRechercheSolvable = sampleWithRequiredData;
        expectedResult = service.addRechercheSolvableToCollectionIfMissing([], rechercheSolvable);
        expect(expectedResult).toEqual([rechercheSolvable]);
      });

      it('should not add a RechercheSolvable to an array that contains it', () => {
        const rechercheSolvable: IRechercheSolvable = sampleWithRequiredData;
        const rechercheSolvableCollection: IRechercheSolvable[] = [
          {
            ...rechercheSolvable,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRechercheSolvableToCollectionIfMissing(rechercheSolvableCollection, rechercheSolvable);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RechercheSolvable to an array that doesn't contain it", () => {
        const rechercheSolvable: IRechercheSolvable = sampleWithRequiredData;
        const rechercheSolvableCollection: IRechercheSolvable[] = [sampleWithPartialData];
        expectedResult = service.addRechercheSolvableToCollectionIfMissing(rechercheSolvableCollection, rechercheSolvable);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rechercheSolvable);
      });

      it('should add only unique RechercheSolvable to an array', () => {
        const rechercheSolvableArray: IRechercheSolvable[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const rechercheSolvableCollection: IRechercheSolvable[] = [sampleWithRequiredData];
        expectedResult = service.addRechercheSolvableToCollectionIfMissing(rechercheSolvableCollection, ...rechercheSolvableArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rechercheSolvable: IRechercheSolvable = sampleWithRequiredData;
        const rechercheSolvable2: IRechercheSolvable = sampleWithPartialData;
        expectedResult = service.addRechercheSolvableToCollectionIfMissing([], rechercheSolvable, rechercheSolvable2);
        expect(expectedResult).toEqual([rechercheSolvable, rechercheSolvable2]);
      });

      it('should accept null and undefined values', () => {
        const rechercheSolvable: IRechercheSolvable = sampleWithRequiredData;
        expectedResult = service.addRechercheSolvableToCollectionIfMissing([], null, rechercheSolvable, undefined);
        expect(expectedResult).toEqual([rechercheSolvable]);
      });

      it('should return initial array if no RechercheSolvable is added', () => {
        const rechercheSolvableCollection: IRechercheSolvable[] = [sampleWithRequiredData];
        expectedResult = service.addRechercheSolvableToCollectionIfMissing(rechercheSolvableCollection, undefined, null);
        expect(expectedResult).toEqual(rechercheSolvableCollection);
      });
    });

    describe('compareRechercheSolvable', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRechercheSolvable(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7543 };
        const entity2 = null;

        const compareResult1 = service.compareRechercheSolvable(entity1, entity2);
        const compareResult2 = service.compareRechercheSolvable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7543 };
        const entity2 = { id: 3658 };

        const compareResult1 = service.compareRechercheSolvable(entity1, entity2);
        const compareResult2 = service.compareRechercheSolvable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7543 };
        const entity2 = { id: 7543 };

        const compareResult1 = service.compareRechercheSolvable(entity1, entity2);
        const compareResult2 = service.compareRechercheSolvable(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
