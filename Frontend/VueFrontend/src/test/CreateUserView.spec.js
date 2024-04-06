import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import CreateUserView from '@/views/loginView/CreateUserView.vue'

describe('CreateUserView', () => {
  it('renders create user form, input fields and create user button', async () => {
    const wrapper = mount(CreateUserView);
    expect(wrapper.find('input[type="text"]').exists()).toBe(true);
    expect(wrapper.find('label[for="username"]').text()).toContain('Username');

    expect(wrapper.find ('input[type="email"]').exists()).toBe(true);
    expect(wrapper.find('label[for="email"]').text()).toContain('Email');

    expect(wrapper.find('input[type="password"]').exists()).toBe(true);
    expect(wrapper.find('label[for="password"]').text()).toContain('Password');

    expect(wrapper.findAll('input[type="password"]').length).toBe(2); // Sjekker at det er to passordfelt
    expect(wrapper.find('label[for="password-confirmation"]').text()).toContain('Confirm Password');
  });

  it('should disable create user button when username are empty', async () => {
    const wrapper = mount(CreateUserView);
    await wrapper.find('input[type="text"]').setValue('');
    await wrapper.find('input[type="email"]').setValue('test@user.com');
    await wrapper.find('input[type="password"]').setValue('password');
    await wrapper.find('input[type="password"]').setValue('password');
    expect(wrapper.find('#register-user').element.disabled).toBeTruthy();
  });

  it('should disable create user button when email are empty', async () => {
    const wrapper = mount(CreateUserView);
    await wrapper.find('input[type="text"]').setValue('testuser');
    await wrapper.find('input[type="email"]').setValue('');
    await wrapper.find('input[type="password"]').setValue('password');
    await wrapper.find('input[type="password"]').setValue('password');
    expect(wrapper.find('#register-user').element.disabled).toBeTruthy();
  });

  it('should disable create user button when password are empty', async () => {
    const wrapper = mount(CreateUserView);
    await wrapper.find('input[type="text"]').setValue('testuser');
    await wrapper.find('input[type="email"]').setValue('test@user.com');
    await wrapper.find('input[type="password"]').setValue('');
    await wrapper.find('input[type="password"]').setValue('password');
    expect(wrapper.find('#register-user').element.disabled).toBeTruthy();
  });

it('should disable create user button when password confirmation are empty', async () => {
    const wrapper = mount(CreateUserView);
    await wrapper.find('input[type="text"]').setValue('testuser');
    await wrapper.find('input[type="email"]').setValue('test@user.com');
    await wrapper.find('input[type="password"]').setValue('password');
    await wrapper.find('input[id="password-confirmation"]').setValue('');
    expect(wrapper.find('#register-user').element.disabled).toBeTruthy();
});

  it('should enable create user button when username, email, password and password confirmation are filled', async () => {
    const wrapper = mount(CreateUserView);
    await wrapper.find('input[type="text"]').setValue('testuser');
    await wrapper.find('input[type="email"]').setValue('test@user.com');
    await wrapper.find('input[type="password"]').setValue('password');
    await wrapper.find('input[id="password-confirmation"]').setValue('password'); // Bruk riktig selector for passordbekreftelse
    expect(wrapper.find('#register-user').element.disabled).toBeFalsy(); // Forvent at knappen er aktivert
  });
});

