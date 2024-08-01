import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ScreenService from './screen.service';
import { type IScreen } from '@/shared/model/screen.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ScreenDetails',
  setup() {
    const screenService = inject('screenService', () => new ScreenService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const screen: Ref<IScreen> = ref({});

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

    return {
      alertService,
      screen,

      previousState,
      t$: useI18n().t,
    };
  },
});
