<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.booking.home.createOrEditLabel"
          data-cy="BookingCreateUpdateHeading"
          v-text="t$('jhipsterApp.booking.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="booking.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="booking.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.bookingID')" for="booking-bookingID"></label>
            <input
              type="text"
              class="form-control"
              name="bookingID"
              id="booking-bookingID"
              data-cy="bookingID"
              :class="{ valid: !v$.bookingID.$invalid, invalid: v$.bookingID.$invalid }"
              v-model="v$.bookingID.$model"
            />
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
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.seatID')" for="booking-seatID"></label>
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
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.paymentStatus')" for="booking-paymentStatus"></label>
            <input
              type="text"
              class="form-control"
              name="paymentStatus"
              id="booking-paymentStatus"
              data-cy="paymentStatus"
              :class="{ valid: !v$.paymentStatus.$invalid, invalid: v$.paymentStatus.$invalid }"
              v-model="v$.paymentStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.booking.movie')" for="booking-movie"></label>
            <select class="form-control" id="booking-movie" data-cy="movie" name="movie" v-model="booking.movie">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="booking.movie && movieOption.id === booking.movie.id ? booking.movie : movieOption"
                v-for="movieOption in movies"
                :key="movieOption.id"
              >
                {{ movieOption.id }}
              </option>
            </select>
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
                {{ customerOption.id }}
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
<script lang="ts" src="./booking-update.component.ts"></script>
