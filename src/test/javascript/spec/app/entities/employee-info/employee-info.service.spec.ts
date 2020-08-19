import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeInfoService } from 'app/entities/employee-info/employee-info.service';
import { IEmployeeInfo, EmployeeInfo } from 'app/shared/model/employee-info.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { Status } from 'app/shared/model/enumerations/status.model';

describe('Service Tests', () => {
  describe('EmployeeInfo Service', () => {
    let injector: TestBed;
    let service: EmployeeInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeInfo;
    let expectedResult: IEmployeeInfo | IEmployeeInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmployeeInfo(
        0,
        'AAAAAAA',
        0,
        GenderType.MALE,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        Status.ACTIVE,
        currentDate,
        currentDate,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            doj: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeeInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            doj: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            doj: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmployeeInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            addressproof: 'BBBBBB',
            photo: 'BBBBBB',
            doj: currentDate.format(DATE_TIME_FORMAT),
            education: 'BBBBBB',
            referredby: 'BBBBBB',
            status: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 1,
            updatedBy: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            doj: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            age: 1,
            gender: 'BBBBBB',
            addressproof: 'BBBBBB',
            photo: 'BBBBBB',
            doj: currentDate.format(DATE_TIME_FORMAT),
            education: 'BBBBBB',
            referredby: 'BBBBBB',
            status: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 1,
            updatedBy: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            doj: currentDate,
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmployeeInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
