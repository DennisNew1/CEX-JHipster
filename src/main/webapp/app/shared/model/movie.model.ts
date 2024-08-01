export interface IMovie {
  id?: number;
  movieId?: string | null;
  title?: string | null;
  genre?: string | null;
  duration?: number | null;
  releaseDate?: Date | null;
}

export class Movie implements IMovie {
  constructor(
    public id?: number,
    public movieId?: string | null,
    public title?: string | null,
    public genre?: string | null,
    public duration?: number | null,
    public releaseDate?: Date | null,
  ) {}
}
