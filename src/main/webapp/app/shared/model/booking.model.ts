import { type IMovie } from '@/shared/model/movie.model';
import { type ICustomer } from '@/shared/model/customer.model';

export interface IBooking {
  id?: number;
  bookingID?: string | null;
  movieTime?: Date | null;
  seatID?: number | null;
  paymentStatus?: string | null;
  movie?: IMovie | null;
  customer?: ICustomer | null;
}

export class Booking implements IBooking {
  constructor(
    public id?: number,
    public bookingID?: string | null,
    public movieTime?: Date | null,
    public seatID?: number | null,
    public paymentStatus?: string | null,
    public movie?: IMovie | null,
    public customer?: ICustomer | null,
  ) {}
}
