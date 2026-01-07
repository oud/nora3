import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDemarche } from '../demarche.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../demarche.test-samples';

import { DemarcheService, RestDemarche } from './demarche.service';

const requireRestSample: RestDemarche = {
  ...sampleWithRequiredData,
  demarcheDate: sampleWithRequiredData.demarcheDate?.format(DATE_FORMAT),
};

describe('Demarche Service', () => {
  let service: DemarcheService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemarche | IDemarche[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DemarcheService);
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

    it('should create a Demarche', () => {
      const demarche = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demarche).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Demarche', () => {
      const demarche = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demarche).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Demarche', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Demarche', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Demarche', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemarcheToCollectionIfMissing', () => {
      it('should add a Demarche to an empty array', () => {
        const demarche: IDemarche = sampleWithRequiredData;
        expectedResult = service.addDemarcheToCollectionIfMissing([], demarche);
        expect(expectedResult).toEqual([demarche]);
      });

      it('should not add a Demarche to an array that contains it', () => {
        const demarche: IDemarche = sampleWithRequiredData;
        const demarcheCollection: IDemarche[] = [
          {
            ...demarche,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemarcheToCollectionIfMissing(demarcheCollection, demarche);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Demarche to an array that doesn't contain it", () => {
        const demarche: IDemarche = sampleWithRequiredData;
        const demarcheCollection: IDemarche[] = [sampleWithPartialData];
        expectedResult = service.addDemarcheToCollectionIfMissing(demarcheCollection, demarche);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demarche);
      });

      it('should add only unique Demarche to an array', () => {
        const demarcheArray: IDemarche[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demarcheCollection: IDemarche[] = [sampleWithRequiredData];
        expectedResult = service.addDemarcheToCollectionIfMissing(demarcheCollection, ...demarcheArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demarche: IDemarche = sampleWithRequiredData;
        const demarche2: IDemarche = sampleWithPartialData;
        expectedResult = service.addDemarcheToCollectionIfMissing([], demarche, demarche2);
        expect(expectedResult).toEqual([demarche, demarche2]);
      });

      it('should accept null and undefined values', () => {
        const demarche: IDemarche = sampleWithRequiredData;
        expectedResult = service.addDemarcheToCollectionIfMissing([], null, demarche, undefined);
        expect(expectedResult).toEqual([demarche]);
      });

      it('should return initial array if no Demarche is added', () => {
        const demarcheCollection: IDemarche[] = [sampleWithRequiredData];
        expectedResult = service.addDemarcheToCollectionIfMissing(demarcheCollection, undefined, null);
        expect(expectedResult).toEqual(demarcheCollection);
      });
    });

    describe('compareDemarche', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemarche(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7199 };
        const entity2 = null;

        const compareResult1 = service.compareDemarche(entity1, entity2);
        const compareResult2 = service.compareDemarche(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7199 };
        const entity2 = { id: 10476 };

        const compareResult1 = service.compareDemarche(entity1, entity2);
        const compareResult2 = service.compareDemarche(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7199 };
        const entity2 = { id: 7199 };

        const compareResult1 = service.compareDemarche(entity1, entity2);
        const compareResult2 = service.compareDemarche(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
