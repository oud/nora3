import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResultatSolvable } from '../resultat-solvable.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../resultat-solvable.test-samples';

import { RestResultatSolvable, ResultatSolvableService } from './resultat-solvable.service';

const requireRestSample: RestResultatSolvable = {
  ...sampleWithRequiredData,
  moisSolvabiliteDate: sampleWithRequiredData.moisSolvabiliteDate?.format(DATE_FORMAT),
};

describe('ResultatSolvable Service', () => {
  let service: ResultatSolvableService;
  let httpMock: HttpTestingController;
  let expectedResult: IResultatSolvable | IResultatSolvable[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ResultatSolvableService);
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

    it('should create a ResultatSolvable', () => {
      const resultatSolvable = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resultatSolvable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResultatSolvable', () => {
      const resultatSolvable = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resultatSolvable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResultatSolvable', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResultatSolvable', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResultatSolvable', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResultatSolvableToCollectionIfMissing', () => {
      it('should add a ResultatSolvable to an empty array', () => {
        const resultatSolvable: IResultatSolvable = sampleWithRequiredData;
        expectedResult = service.addResultatSolvableToCollectionIfMissing([], resultatSolvable);
        expect(expectedResult).toEqual([resultatSolvable]);
      });

      it('should not add a ResultatSolvable to an array that contains it', () => {
        const resultatSolvable: IResultatSolvable = sampleWithRequiredData;
        const resultatSolvableCollection: IResultatSolvable[] = [
          {
            ...resultatSolvable,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResultatSolvableToCollectionIfMissing(resultatSolvableCollection, resultatSolvable);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResultatSolvable to an array that doesn't contain it", () => {
        const resultatSolvable: IResultatSolvable = sampleWithRequiredData;
        const resultatSolvableCollection: IResultatSolvable[] = [sampleWithPartialData];
        expectedResult = service.addResultatSolvableToCollectionIfMissing(resultatSolvableCollection, resultatSolvable);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultatSolvable);
      });

      it('should add only unique ResultatSolvable to an array', () => {
        const resultatSolvableArray: IResultatSolvable[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const resultatSolvableCollection: IResultatSolvable[] = [sampleWithRequiredData];
        expectedResult = service.addResultatSolvableToCollectionIfMissing(resultatSolvableCollection, ...resultatSolvableArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resultatSolvable: IResultatSolvable = sampleWithRequiredData;
        const resultatSolvable2: IResultatSolvable = sampleWithPartialData;
        expectedResult = service.addResultatSolvableToCollectionIfMissing([], resultatSolvable, resultatSolvable2);
        expect(expectedResult).toEqual([resultatSolvable, resultatSolvable2]);
      });

      it('should accept null and undefined values', () => {
        const resultatSolvable: IResultatSolvable = sampleWithRequiredData;
        expectedResult = service.addResultatSolvableToCollectionIfMissing([], null, resultatSolvable, undefined);
        expect(expectedResult).toEqual([resultatSolvable]);
      });

      it('should return initial array if no ResultatSolvable is added', () => {
        const resultatSolvableCollection: IResultatSolvable[] = [sampleWithRequiredData];
        expectedResult = service.addResultatSolvableToCollectionIfMissing(resultatSolvableCollection, undefined, null);
        expect(expectedResult).toEqual(resultatSolvableCollection);
      });
    });

    describe('compareResultatSolvable', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResultatSolvable(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 3195 };
        const entity2 = null;

        const compareResult1 = service.compareResultatSolvable(entity1, entity2);
        const compareResult2 = service.compareResultatSolvable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 3195 };
        const entity2 = { id: 29889 };

        const compareResult1 = service.compareResultatSolvable(entity1, entity2);
        const compareResult2 = service.compareResultatSolvable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 3195 };
        const entity2 = { id: 3195 };

        const compareResult1 = service.compareResultatSolvable(entity1, entity2);
        const compareResult2 = service.compareResultatSolvable(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
