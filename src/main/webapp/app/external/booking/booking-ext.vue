<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="jhipsterApp.booking.home.createOrEditLabel" data-cy="BookingCreateUpdateHeading" value="">Create a booking</h2>
        <div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.movie')" for="booking-movie"></label>
            <select class="form-control" id="booking-movie" data-cy="movie" name="movie" v-model="booking.movie">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="booking.movie && movieOption.id === booking.movie.id ? booking.movie : movieOption"
                v-for="movieOption in movies"
                :key="movieOption.id"
              >
                {{ movieOption.title }}
              </option>
            </select>
          </div>
          <div class="form-group" v-if="booking.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="booking.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.movieTime')" for="booking-movieTime"></label>
            <div class="d-flex">
              <input
                id="booking-movieTime"
                data-cy="movieTime"
                type="datetime-local"
                class="form-control"
                name="movieTime"
                :class="{ valid: !v$.movieTime.$invalid, invalid: v$.movieTime.$invalid }"
                :value="convertDateTimeFromServer(v$.movieTime.$model)"
                @change="updateZonedDateTimeField('movieTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="booking-seatID">Seat Number</label>
            <input
              type="number"
              class="form-control"
              name="seatID"
              id="booking-seatID"
              data-cy="seatID"
              :class="{ valid: !v$.seatID.$invalid, invalid: v$.seatID.$invalid }"
              v-model.number="v$.seatID.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.customer')" for="booking-customer"></label>
            <select class="form-control" id="booking-customer" data-cy="customer" name="customer" v-model="booking.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="booking.customer && customerOption.id === booking.customer.id ? booking.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./booking-ext.ts"></script>
