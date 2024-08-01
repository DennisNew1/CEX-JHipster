import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MovieService from './movie.service';
import { useDateFormat } from '@/shared/composables';
import { type IMovie } from '@/shared/model/movie.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MovieDetails',
  setup() {
    const dateFormat = useDateFormat();
    const movieService = inject('movieService', () => new MovieService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const movie: Ref<IMovie> = ref({});

    const retrieveMovie = async movieId => {
      try {
        const res = await movieService().find(movieId);
        movie.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.movieId) {
      retrieveMovie(route.params.movieId);
    }

    return {
      ...dateFormat,
      alertService,
      movie,

      previousState,
      t$: useI18n().t,
    };
  },
});
