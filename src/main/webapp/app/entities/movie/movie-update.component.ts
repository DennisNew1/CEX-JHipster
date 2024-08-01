import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MovieService from './movie.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IMovie, Movie } from '@/shared/model/movie.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MovieUpdate',
  setup() {
    const movieService = inject('movieService', () => new MovieService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const movie: Ref<IMovie> = ref(new Movie());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMovie = async movieId => {
      try {
        const res = await movieService().find(movieId);
        res.releaseDate = new Date(res.releaseDate);
        movie.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.movieId) {
      retrieveMovie(route.params.movieId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      movieId: {},
      title: {},
      genre: {},
      duration: {},
      releaseDate: {},
    };
    const v$ = useVuelidate(validationRules, movie as any);
    v$.value.$validate();

    return {
      movieService,
      alertService,
      movie,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: movie }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.movie.id) {
        this.movieService()
          .update(this.movie)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.movie.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.movieService()
          .create(this.movie)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.movie.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
