import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICreancier } from '../creancier.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../creancier.test-samples';

import { CreancierService, RestCreancier } from './creancier.service';

const requireRestSample: RestCreancier = {
  ...sampleWithRequiredData,
  situationFamilialeDebutDate: sampleWithRequiredData.situationFamilialeDebutDate?.format(DATE_FORMAT),
};

describe('Creancier Service', () => {
  let service: CreancierService;
  let httpMock: HttpTestingController;
  let expectedResult: ICreancier | ICreancier[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CreancierService);
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

    it('should create a Creancier', () => {
      const creancier = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(creancier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Creancier', () => {
      const creancier = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(creancier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Creancier', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Creancier', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Creancier', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCreancierToCollectionIfMissing', () => {
      it('should add a Creancier to an empty array', () => {
        const creancier: ICreancier = sampleWithRequiredData;
        expectedResult = service.addCreancierToCollectionIfMissing([], creancier);
        expect(expectedResult).toEqual([creancier]);
      });

      it('should not add a Creancier to an array that contains it', () => {
        const creancier: ICreancier = sampleWithRequiredData;
        const creancierCollection: ICreancier[] = [
          {
            ...creancier,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCreancierToCollectionIfMissing(creancierCollection, creancier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Creancier to an array that doesn't contain it", () => {
        const creancier: ICreancier = sampleWithRequiredData;
        const creancierCollection: ICreancier[] = [sampleWithPartialData];
        expectedResult = service.addCreancierToCollectionIfMissing(creancierCollection, creancier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(creancier);
      });

      it('should add only unique Creancier to an array', () => {
        const creancierArray: ICreancier[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const creancierCollection: ICreancier[] = [sampleWithRequiredData];
        expectedResult = service.addCreancierToCollectionIfMissing(creancierCollection, ...creancierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const creancier: ICreancier = sampleWithRequiredData;
        const creancier2: ICreancier = sampleWithPartialData;
        expectedResult = service.addCreancierToCollectionIfMissing([], creancier, creancier2);
        expect(expectedResult).toEqual([creancier, creancier2]);
      });

      it('should accept null and undefined values', () => {
        const creancier: ICreancier = sampleWithRequiredData;
        expectedResult = service.addCreancierToCollectionIfMissing([], null, creancier, undefined);
        expect(expectedResult).toEqual([creancier]);
      });

      it('should return initial array if no Creancier is added', () => {
        const creancierCollection: ICreancier[] = [sampleWithRequiredData];
        expectedResult = service.addCreancierToCollectionIfMissing(creancierCollection, undefined, null);
        expect(expectedResult).toEqual(creancierCollection);
      });
    });

    describe('compareCreancier', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCreancier(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 32390 };
        const entity2 = null;

        const compareResult1 = service.compareCreancier(entity1, entity2);
        const compareResult2 = service.compareCreancier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 32390 };
        const entity2 = { id: 1793 };

        const compareResult1 = service.compareCreancier(entity1, entity2);
        const compareResult2 = service.compareCreancier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 32390 };
        const entity2 = { id: 32390 };

        const compareResult1 = service.compareCreancier(entity1, entity2);
        const compareResult2 = service.compareCreancier(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
