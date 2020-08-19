import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFeedBack, FeedBack } from 'app/shared/model/feed-back.model';
import { FeedBackService } from './feed-back.service';

@Component({
  selector: 'jhi-feed-back-update',
  templateUrl: './feed-back-update.component.html',
})
export class FeedBackUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    comments: [],
    feedbackStatus: [],
  });

  constructor(protected feedBackService: FeedBackService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedBack }) => {
      this.updateForm(feedBack);
    });
  }

  updateForm(feedBack: IFeedBack): void {
    this.editForm.patchValue({
      id: feedBack.id,
      comments: feedBack.comments,
      feedbackStatus: feedBack.feedbackStatus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedBack = this.createFromForm();
    if (feedBack.id !== undefined) {
      this.subscribeToSaveResponse(this.feedBackService.update(feedBack));
    } else {
      this.subscribeToSaveResponse(this.feedBackService.create(feedBack));
    }
  }

  private createFromForm(): IFeedBack {
    return {
      ...new FeedBack(),
      id: this.editForm.get(['id'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      feedbackStatus: this.editForm.get(['feedbackStatus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedBack>>): void {
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
}
