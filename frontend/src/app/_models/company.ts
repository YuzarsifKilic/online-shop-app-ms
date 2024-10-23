import {Address} from "./address";

export class Company {
  id!: string;
  email!: string;
  companyName!: string;
  companyLogoUrl!: string;
  address!: Address;
}
