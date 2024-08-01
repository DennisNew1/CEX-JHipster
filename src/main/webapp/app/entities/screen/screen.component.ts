import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ScreenService from './screen.service';
import { type IScreen } from '@/shared/model/screen.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Screen',
  setup() {
    const { t: t$ } = useI18n();
    const screenService = inject('screenService', () => new ScreenService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const screens: Ref<IScreen[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveScreens = async () => {
      isFetching.value = true;
      try {
        const res = await screenService().retrieve();
        screens.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveScreens();
    };

    onMounted(async () => {
      await retrieveScreens();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IScreen) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeScreen = async () => {
      try {
        await screenService().delete(removeId.value);
        const message = t$('jhipsterApp.screen.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveScreens();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      screens,
      handleSyncList,
      isFetching,
      retrieveScreens,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeScreen,
      t$,
    };
  },
});
