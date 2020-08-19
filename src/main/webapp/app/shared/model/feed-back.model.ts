import { FeedbackStatus } from 'app/shared/model/enumerations/feedback-status.model';

export interface IFeedBack {
  id?: number;
  comments?: string;
  feedbackStatus?: FeedbackStatus;
}

export class FeedBack implements IFeedBack {
  constructor(public id?: number, public comments?: string, public feedbackStatus?: FeedbackStatus) {}
}
