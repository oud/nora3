import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IDebiteur } from '../debiteur.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../debiteur.test-samples';

import { DebiteurService } from './debiteur.service';

const requireRestSample: IDebiteur = {
  ...sampleWithRequiredData,
};

describe('Debiteur Service', () => {
  let service: DebiteurService;
  let httpMock: HttpTestingController;
  let expectedResult: IDebiteur | IDebiteur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DebiteurService);
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

    it('should create a Debiteur', () => {
      const debiteur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(debiteur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Debiteur', () => {
      const debiteur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(debiteur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Debiteur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Debiteur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Debiteur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDebiteurToCollectionIfMissing', () => {
      it('should add a Debiteur to an empty array', () => {
        const debiteur: IDebiteur = sampleWithRequiredData;
        expectedResult = service.addDebiteurToCollectionIfMissing([], debiteur);
        expect(expectedResult).toEqual([debiteur]);
      });

      it('should not add a Debiteur to an array that contains it', () => {
        const debiteur: IDebiteur = sampleWithRequiredData;
        const debiteurCollection: IDebiteur[] = [
          {
            ...debiteur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDebiteurToCollectionIfMissing(debiteurCollection, debiteur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Debiteur to an array that doesn't contain it", () => {
        const debiteur: IDebiteur = sampleWithRequiredData;
        const debiteurCollection: IDebiteur[] = [sampleWithPartialData];
        expectedResult = service.addDebiteurToCollectionIfMissing(debiteurCollection, debiteur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(debiteur);
      });

      it('should add only unique Debiteur to an array', () => {
        const debiteurArray: IDebiteur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const debiteurCollection: IDebiteur[] = [sampleWithRequiredData];
        expectedResult = service.addDebiteurToCollectionIfMissing(debiteurCollection, ...debiteurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const debiteur: IDebiteur = sampleWithRequiredData;
        const debiteur2: IDebiteur = sampleWithPartialData;
        expectedResult = service.addDebiteurToCollectionIfMissing([], debiteur, debiteur2);
        expect(expectedResult).toEqual([debiteur, debiteur2]);
      });

      it('should accept null and undefined values', () => {
        const debiteur: IDebiteur = sampleWithRequiredData;
        expectedResult = service.addDebiteurToCollectionIfMissing([], null, debiteur, undefined);
        expect(expectedResult).toEqual([debiteur]);
      });

      it('should return initial array if no Debiteur is added', () => {
        const debiteurCollection: IDebiteur[] = [sampleWithRequiredData];
        expectedResult = service.addDebiteurToCollectionIfMissing(debiteurCollection, undefined, null);
        expect(expectedResult).toEqual(debiteurCollection);
      });
    });

    describe('compareDebiteur', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDebiteur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18158 };
        const entity2 = null;

        const compareResult1 = service.compareDebiteur(entity1, entity2);
        const compareResult2 = service.compareDebiteur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18158 };
        const entity2 = { id: 6034 };

        const compareResult1 = service.compareDebiteur(entity1, entity2);
        const compareResult2 = service.compareDebiteur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18158 };
        const entity2 = { id: 18158 };

        const compareResult1 = service.compareDebiteur(entity1, entity2);
        const compareResult2 = service.compareDebiteur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
