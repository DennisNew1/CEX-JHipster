import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Movie = () => import('@/entities/movie/movie.vue');
const MovieUpdate = () => import('@/entities/movie/movie-update.vue');
const MovieDetails = () => import('@/entities/movie/movie-details.vue');

const Screen = () => import('@/entities/screen/screen.vue');
const ScreenUpdate = () => import('@/entities/screen/screen-update.vue');
const ScreenDetails = () => import('@/entities/screen/screen-details.vue');

const Booking = () => import('@/entities/booking/booking.vue');
const BookingUpdate = () => import('@/entities/booking/booking-update.vue');
const BookingDetails = () => import('@/entities/booking/booking-details.vue');

const Customer = () => import('@/entities/customer/customer.vue');
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'movie',
      name: 'Movie',
      component: Movie,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/new',
      name: 'MovieCreate',
      component: MovieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/:movieId/edit',
      name: 'MovieEdit',
      component: MovieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'movie/:movieId/view',
      name: 'MovieView',
      component: MovieDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'screen',
      name: 'Screen',
      component: Screen,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'screen/new',
      name: 'ScreenCreate',
      component: ScreenUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'screen/:screenId/edit',
      name: 'ScreenEdit',
      component: ScreenUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'screen/:screenId/view',
      name: 'ScreenView',
      component: ScreenDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'booking',
      name: 'Booking',
      component: Booking,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'booking/new',
      name: 'BookingCreate',
      component: BookingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'booking/:bookingId/edit',
      name: 'BookingEdit',
      component: BookingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'booking/:bookingId/view',
      name: 'BookingView',
      component: BookingDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
