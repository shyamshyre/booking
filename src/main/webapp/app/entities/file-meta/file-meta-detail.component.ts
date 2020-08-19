import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileMeta } from 'app/shared/model/file-meta.model';

@Component({
  selector: 'jhi-file-meta-detail',
  templateUrl: './file-meta-detail.component.html',
})
export class FileMetaDetailComponent implements OnInit {
  fileMeta: IFileMeta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileMeta }) => (this.fileMeta = fileMeta));
  }

  previousState(): void {
    window.history.back();
  }
}
