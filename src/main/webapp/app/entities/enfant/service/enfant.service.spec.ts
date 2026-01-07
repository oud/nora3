import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IEnfant } from '../enfant.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../enfant.test-samples';

import { EnfantService } from './enfant.service';

const requireRestSample: IEnfant = {
  ...sampleWithRequiredData,
};

describe('Enfant Service', () => {
  let service: EnfantService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnfant | IEnfant[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EnfantService);
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

    it('should create a Enfant', () => {
      const enfant = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enfant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Enfant', () => {
      const enfant = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enfant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Enfant', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Enfant', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Enfant', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnfantToCollectionIfMissing', () => {
      it('should add a Enfant to an empty array', () => {
        const enfant: IEnfant = sampleWithRequiredData;
        expectedResult = service.addEnfantToCollectionIfMissing([], enfant);
        expect(expectedResult).toEqual([enfant]);
      });

      it('should not add a Enfant to an array that contains it', () => {
        const enfant: IEnfant = sampleWithRequiredData;
        const enfantCollection: IEnfant[] = [
          {
            ...enfant,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnfantToCollectionIfMissing(enfantCollection, enfant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Enfant to an array that doesn't contain it", () => {
        const enfant: IEnfant = sampleWithRequiredData;
        const enfantCollection: IEnfant[] = [sampleWithPartialData];
        expectedResult = service.addEnfantToCollectionIfMissing(enfantCollection, enfant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enfant);
      });

      it('should add only unique Enfant to an array', () => {
        const enfantArray: IEnfant[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enfantCollection: IEnfant[] = [sampleWithRequiredData];
        expectedResult = service.addEnfantToCollectionIfMissing(enfantCollection, ...enfantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enfant: IEnfant = sampleWithRequiredData;
        const enfant2: IEnfant = sampleWithPartialData;
        expectedResult = service.addEnfantToCollectionIfMissing([], enfant, enfant2);
        expect(expectedResult).toEqual([enfant, enfant2]);
      });

      it('should accept null and undefined values', () => {
        const enfant: IEnfant = sampleWithRequiredData;
        expectedResult = service.addEnfantToCollectionIfMissing([], null, enfant, undefined);
        expect(expectedResult).toEqual([enfant]);
      });

      it('should return initial array if no Enfant is added', () => {
        const enfantCollection: IEnfant[] = [sampleWithRequiredData];
        expectedResult = service.addEnfantToCollectionIfMissing(enfantCollection, undefined, null);
        expect(expectedResult).toEqual(enfantCollection);
      });
    });

    describe('compareEnfant', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnfant(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 8992 };
        const entity2 = null;

        const compareResult1 = service.compareEnfant(entity1, entity2);
        const compareResult2 = service.compareEnfant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 8992 };
        const entity2 = { id: 10156 };

        const compareResult1 = service.compareEnfant(entity1, entity2);
        const compareResult2 = service.compareEnfant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 8992 };
        const entity2 = { id: 8992 };

        const compareResult1 = service.compareEnfant(entity1, entity2);
        const compareResult2 = service.compareEnfant(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
