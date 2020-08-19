import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerInfoService } from 'app/entities/customer-info/customer-info.service';
import { ICustomerInfo, CustomerInfo } from 'app/shared/model/customer-info.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { MStatus } from 'app/shared/model/enumerations/m-status.model';

describe('Service Tests', () => {
  describe('CustomerInfo Service', () => {
    let injector: TestBed;
    let service: CustomerInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomerInfo;
    let expectedResult: ICustomerInfo | ICustomerInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CustomerInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CustomerInfo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        GenderType.MALE,
        MStatus.SINGLE,
        'AAAAAAA',
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

      it('should create a CustomerInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new CustomerInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomerInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            photo: 'BBBBBB',
            age: 1,
            phno: 1,
            email: 'BBBBBB',
            gender: 'BBBBBB',
            status: 'BBBBBB',
            addressproof: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 1,
            updatedBy: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of CustomerInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            photo: 'BBBBBB',
            age: 1,
            phno: 1,
            email: 'BBBBBB',
            gender: 'BBBBBB',
            status: 'BBBBBB',
            addressproof: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            updatedDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 1,
            updatedBy: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a CustomerInfo', () => {
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
