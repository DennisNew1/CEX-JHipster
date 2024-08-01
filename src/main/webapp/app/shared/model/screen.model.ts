import { type IMovie } from '@/shared/model/movie.model';

export interface IScreen {
  id?: number;
  screenID?: string | null;
  name?: string | null;
  totalSeats?: number | null;
  movie?: IMovie | null;
}

export class Screen implements IScreen {
  constructor(
    public id?: number,
    public screenID?: string | null,
    public name?: string | null,
    public totalSeats?: number | null,
    public movie?: IMovie | null,
  ) {}
}
