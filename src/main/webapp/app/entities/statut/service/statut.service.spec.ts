import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IStatut } from '../statut.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../statut.test-samples';

import { StatutService } from './statut.service';

const requireRestSample: IStatut = {
  ...sampleWithRequiredData,
};

describe('Statut Service', () => {
  let service: StatutService;
  let httpMock: HttpTestingController;
  let expectedResult: IStatut | IStatut[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(StatutService);
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

    it('should create a Statut', () => {
      const statut = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(statut).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Statut', () => {
      const statut = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(statut).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Statut', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Statut', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Statut', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStatutToCollectionIfMissing', () => {
      it('should add a Statut to an empty array', () => {
        const statut: IStatut = sampleWithRequiredData;
        expectedResult = service.addStatutToCollectionIfMissing([], statut);
        expect(expectedResult).toEqual([statut]);
      });

      it('should not add a Statut to an array that contains it', () => {
        const statut: IStatut = sampleWithRequiredData;
        const statutCollection: IStatut[] = [
          {
            ...statut,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStatutToCollectionIfMissing(statutCollection, statut);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Statut to an array that doesn't contain it", () => {
        const statut: IStatut = sampleWithRequiredData;
        const statutCollection: IStatut[] = [sampleWithPartialData];
        expectedResult = service.addStatutToCollectionIfMissing(statutCollection, statut);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statut);
      });

      it('should add only unique Statut to an array', () => {
        const statutArray: IStatut[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const statutCollection: IStatut[] = [sampleWithRequiredData];
        expectedResult = service.addStatutToCollectionIfMissing(statutCollection, ...statutArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const statut: IStatut = sampleWithRequiredData;
        const statut2: IStatut = sampleWithPartialData;
        expectedResult = service.addStatutToCollectionIfMissing([], statut, statut2);
        expect(expectedResult).toEqual([statut, statut2]);
      });

      it('should accept null and undefined values', () => {
        const statut: IStatut = sampleWithRequiredData;
        expectedResult = service.addStatutToCollectionIfMissing([], null, statut, undefined);
        expect(expectedResult).toEqual([statut]);
      });

      it('should return initial array if no Statut is added', () => {
        const statutCollection: IStatut[] = [sampleWithRequiredData];
        expectedResult = service.addStatutToCollectionIfMissing(statutCollection, undefined, null);
        expect(expectedResult).toEqual(statutCollection);
      });
    });

    describe('compareStatut', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStatut(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 10699 };
        const entity2 = null;

        const compareResult1 = service.compareStatut(entity1, entity2);
        const compareResult2 = service.compareStatut(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 10699 };
        const entity2 = { id: 9043 };

        const compareResult1 = service.compareStatut(entity1, entity2);
        const compareResult2 = service.compareStatut(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 10699 };
        const entity2 = { id: 10699 };

        const compareResult1 = service.compareStatut(entity1, entity2);
        const compareResult2 = service.compareStatut(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
