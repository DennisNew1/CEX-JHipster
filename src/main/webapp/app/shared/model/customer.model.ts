export interface ICustomer {
  id?: number;
  customerID?: string | null;
  name?: string | null;
  email?: string | null;
  phoneNumber?: number | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public customerID?: string | null,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: number | null,
  ) {}
}
