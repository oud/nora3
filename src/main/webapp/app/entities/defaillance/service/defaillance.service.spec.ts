import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDefaillance } from '../defaillance.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../defaillance.test-samples';

import { DefaillanceService, RestDefaillance } from './defaillance.service';

const requireRestSample: RestDefaillance = {
  ...sampleWithRequiredData,
  moisDefaillance: sampleWithRequiredData.moisDefaillance?.format(DATE_FORMAT),
};

describe('Defaillance Service', () => {
  let service: DefaillanceService;
  let httpMock: HttpTestingController;
  let expectedResult: IDefaillance | IDefaillance[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DefaillanceService);
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

    it('should create a Defaillance', () => {
      const defaillance = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(defaillance).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Defaillance', () => {
      const defaillance = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(defaillance).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Defaillance', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Defaillance', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Defaillance', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDefaillanceToCollectionIfMissing', () => {
      it('should add a Defaillance to an empty array', () => {
        const defaillance: IDefaillance = sampleWithRequiredData;
        expectedResult = service.addDefaillanceToCollectionIfMissing([], defaillance);
        expect(expectedResult).toEqual([defaillance]);
      });

      it('should not add a Defaillance to an array that contains it', () => {
        const defaillance: IDefaillance = sampleWithRequiredData;
        const defaillanceCollection: IDefaillance[] = [
          {
            ...defaillance,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDefaillanceToCollectionIfMissing(defaillanceCollection, defaillance);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Defaillance to an array that doesn't contain it", () => {
        const defaillance: IDefaillance = sampleWithRequiredData;
        const defaillanceCollection: IDefaillance[] = [sampleWithPartialData];
        expectedResult = service.addDefaillanceToCollectionIfMissing(defaillanceCollection, defaillance);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(defaillance);
      });

      it('should add only unique Defaillance to an array', () => {
        const defaillanceArray: IDefaillance[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const defaillanceCollection: IDefaillance[] = [sampleWithRequiredData];
        expectedResult = service.addDefaillanceToCollectionIfMissing(defaillanceCollection, ...defaillanceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const defaillance: IDefaillance = sampleWithRequiredData;
        const defaillance2: IDefaillance = sampleWithPartialData;
        expectedResult = service.addDefaillanceToCollectionIfMissing([], defaillance, defaillance2);
        expect(expectedResult).toEqual([defaillance, defaillance2]);
      });

      it('should accept null and undefined values', () => {
        const defaillance: IDefaillance = sampleWithRequiredData;
        expectedResult = service.addDefaillanceToCollectionIfMissing([], null, defaillance, undefined);
        expect(expectedResult).toEqual([defaillance]);
      });

      it('should return initial array if no Defaillance is added', () => {
        const defaillanceCollection: IDefaillance[] = [sampleWithRequiredData];
        expectedResult = service.addDefaillanceToCollectionIfMissing(defaillanceCollection, undefined, null);
        expect(expectedResult).toEqual(defaillanceCollection);
      });
    });

    describe('compareDefaillance', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDefaillance(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 5429 };
        const entity2 = null;

        const compareResult1 = service.compareDefaillance(entity1, entity2);
        const compareResult2 = service.compareDefaillance(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 5429 };
        const entity2 = { id: 10347 };

        const compareResult1 = service.compareDefaillance(entity1, entity2);
        const compareResult2 = service.compareDefaillance(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 5429 };
        const entity2 = { id: 5429 };

        const compareResult1 = service.compareDefaillance(entity1, entity2);
        const compareResult2 = service.compareDefaillance(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
