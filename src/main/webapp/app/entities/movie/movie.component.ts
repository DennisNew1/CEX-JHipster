import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MovieService from './movie.service';
import { type IMovie } from '@/shared/model/movie.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Movie',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const movieService = inject('movieService', () => new MovieService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const movies: Ref<IMovie[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMovies = async () => {
      isFetching.value = true;
      try {
        const res = await movieService().retrieve();
        movies.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMovies();
    };

    onMounted(async () => {
      await retrieveMovies();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMovie) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMovie = async () => {
      try {
        await movieService().delete(removeId.value);
        const message = t$('jhipsterApp.movie.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMovies();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      movies,
      handleSyncList,
      isFetching,
      retrieveMovies,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMovie,
      t$,
    };
  },
});
