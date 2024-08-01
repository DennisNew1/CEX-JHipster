import { defineComponent, provide } from 'vue';

import MovieService from './movie/movie.service';
import ScreenService from './screen/screen.service';
import BookingService from './booking/booking.service';
import CustomerService from './customer/customer.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('movieService', () => new MovieService());
    provide('screenService', () => new ScreenService());
    provide('bookingService', () => new BookingService());
    provide('customerService', () => new CustomerService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
