import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmployeeInfo, EmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from './employee-info.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-employee-info-update',
  templateUrl: './employee-info-update.component.html',
})
export class EmployeeInfoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    age: [],
    gender: [],
    addressproof: [],
    photo: [],
    doj: [],
    education: [],
    referredby: [],
    status: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    user: [],
  });

  constructor(
    protected employeeInfoService: EmployeeInfoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeInfo }) => {
      if (!employeeInfo.id) {
        const today = moment().startOf('day');
        employeeInfo.doj = today;
        employeeInfo.createdDate = today;
        employeeInfo.updatedDate = today;
      }

      this.updateForm(employeeInfo);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(employeeInfo: IEmployeeInfo): void {
    this.editForm.patchValue({
      id: employeeInfo.id,
      name: employeeInfo.name,
      age: employeeInfo.age,
      gender: employeeInfo.gender,
      addressproof: employeeInfo.addressproof,
      photo: employeeInfo.photo,
      doj: employeeInfo.doj ? employeeInfo.doj.format(DATE_TIME_FORMAT) : null,
      education: employeeInfo.education,
      referredby: employeeInfo.referredby,
      status: employeeInfo.status,
      createdDate: employeeInfo.createdDate ? employeeInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: employeeInfo.updatedDate ? employeeInfo.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: employeeInfo.createdBy,
      updatedBy: employeeInfo.updatedBy,
      user: employeeInfo.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeInfo = this.createFromForm();
    if (employeeInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeInfoService.update(employeeInfo));
    } else {
      this.subscribeToSaveResponse(this.employeeInfoService.create(employeeInfo));
    }
  }

  private createFromForm(): IEmployeeInfo {
    return {
      ...new EmployeeInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      addressproof: this.editForm.get(['addressproof'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      doj: this.editForm.get(['doj'])!.value ? moment(this.editForm.get(['doj'])!.value, DATE_TIME_FORMAT) : undefined,
      education: this.editForm.get(['education'])!.value,
      referredby: this.editForm.get(['referredby'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
