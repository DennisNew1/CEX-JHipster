import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ScreenService from './screen.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MovieService from '@/entities/movie/movie.service';
import { type IMovie } from '@/shared/model/movie.model';
import { type IScreen, Screen } from '@/shared/model/screen.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ScreenUpdate',
  setup() {
    const screenService = inject('screenService', () => new ScreenService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const screen: Ref<IScreen> = ref(new Screen());

    const movieService = inject('movieService', () => new MovieService());

    const movies: Ref<IMovie[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveScreen = async screenId => {
      try {
        const res = await screenService().find(screenId);
        screen.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.screenId) {
      retrieveScreen(route.params.screenId);
    }

    const initRelationships = () => {
      movieService()
        .retrieve()
        .then(res => {
          movies.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      screenID: {},
      name: {},
      totalSeats: {},
      movie: {},
    };
    const v$ = useVuelidate(validationRules, screen as any);
    v$.value.$validate();

    return {
      screenService,
      alertService,
      screen,
      previousState,
      isSaving,
      currentLanguage,
      movies,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.screen.id) {
        this.screenService()
          .update(this.screen)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApp.screen.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.screenService()
          .create(this.screen)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApp.screen.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
