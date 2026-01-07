import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISituation } from '../situation.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../situation.test-samples';

import { RestSituation, SituationService } from './situation.service';

const requireRestSample: RestSituation = {
  ...sampleWithRequiredData,
  situationProDebutDate: sampleWithRequiredData.situationProDebutDate?.format(DATE_FORMAT),
  situationProfinDate: sampleWithRequiredData.situationProfinDate?.format(DATE_FORMAT),
};

describe('Situation Service', () => {
  let service: SituationService;
  let httpMock: HttpTestingController;
  let expectedResult: ISituation | ISituation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SituationService);
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

    it('should create a Situation', () => {
      const situation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(situation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Situation', () => {
      const situation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(situation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Situation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Situation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Situation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSituationToCollectionIfMissing', () => {
      it('should add a Situation to an empty array', () => {
        const situation: ISituation = sampleWithRequiredData;
        expectedResult = service.addSituationToCollectionIfMissing([], situation);
        expect(expectedResult).toEqual([situation]);
      });

      it('should not add a Situation to an array that contains it', () => {
        const situation: ISituation = sampleWithRequiredData;
        const situationCollection: ISituation[] = [
          {
            ...situation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, situation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Situation to an array that doesn't contain it", () => {
        const situation: ISituation = sampleWithRequiredData;
        const situationCollection: ISituation[] = [sampleWithPartialData];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, situation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situation);
      });

      it('should add only unique Situation to an array', () => {
        const situationArray: ISituation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const situationCollection: ISituation[] = [sampleWithRequiredData];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, ...situationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const situation: ISituation = sampleWithRequiredData;
        const situation2: ISituation = sampleWithPartialData;
        expectedResult = service.addSituationToCollectionIfMissing([], situation, situation2);
        expect(expectedResult).toEqual([situation, situation2]);
      });

      it('should accept null and undefined values', () => {
        const situation: ISituation = sampleWithRequiredData;
        expectedResult = service.addSituationToCollectionIfMissing([], null, situation, undefined);
        expect(expectedResult).toEqual([situation]);
      });

      it('should return initial array if no Situation is added', () => {
        const situationCollection: ISituation[] = [sampleWithRequiredData];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, undefined, null);
        expect(expectedResult).toEqual(situationCollection);
      });
    });

    describe('compareSituation', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSituation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 28437 };
        const entity2 = null;

        const compareResult1 = service.compareSituation(entity1, entity2);
        const compareResult2 = service.compareSituation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 28437 };
        const entity2 = { id: 12449 };

        const compareResult1 = service.compareSituation(entity1, entity2);
        const compareResult2 = service.compareSituation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 28437 };
        const entity2 = { id: 28437 };

        const compareResult1 = service.compareSituation(entity1, entity2);
        const compareResult2 = service.compareSituation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
