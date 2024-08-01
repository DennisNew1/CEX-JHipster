import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import BookingService from './booking.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MovieService from '@/entities/movie/movie.service';
import { type IMovie } from '@/shared/model/movie.model';
import CustomerService from '@/entities/customer/customer.service';
import { type ICustomer } from '@/shared/model/customer.model';
import { type IBooking, Booking } from '@/shared/model/booking.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BookingUpdate',
  setup() {
    const bookingService = inject('bookingService', () => new BookingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const booking: Ref<IBooking> = ref(new Booking());

    const movieService = inject('movieService', () => new MovieService());

    const movies: Ref<IMovie[]> = ref([]);

    const customerService = inject('customerService', () => new CustomerService());

    const customers: Ref<ICustomer[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveBooking = async bookingId => {
      try {
        const res = await bookingService().find(bookingId);
        res.movieTime = new Date(res.movieTime);
        booking.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.bookingId) {
      retrieveBooking(route.params.bookingId);
    }

    const initRelationships = () => {
      movieService()
        .retrieve()
        .then(res => {
          movies.value = res.data;
        });
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      bookingID: {},
      movieTime: {},
      seatID: {},
      paymentStatus: {},
      movie: {},
      customer: {},
    };
    const v$ = useVuelidate(validationRules, booking as any);
    v$.value.$validate();

    return {
      bookingService,
      alertService,
      booking,
      previousState,
      isSaving,
      currentLanguage,
      movies,
      customers,
      v$,
      ...useDateFormat({ entityRef: booking }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.booking.id) {
        this.bookingService()
          .update(this.booking)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.booking.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.bookingService()
          .create(this.booking)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.booking.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
