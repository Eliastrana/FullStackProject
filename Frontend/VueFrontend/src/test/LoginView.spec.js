import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import LoginView from "/src/views/loginView/LoginView.vue";

describe('LoginView.vue', () => {
  it('renders login form with username, password fields, and submit & register buttons', async () => {
    const wrapper = mount(LoginView);
    // Sjekker om brukernavnsfeltet renderes
    expect(wrapper.find('input[type="text"]').exists()).toBe(true);
    expect(wrapper.find('label[for="username"]').text()).toContain('Username');
    // Sjekker om passordfeltet renderes
    expect(wrapper.find('input[type="password"]').exists()).toBe(true);
    expect(wrapper.find('label[for="password"]').text()).toContain('Password');
    // Sjekker om innloggingsknappen renderes
    expect(wrapper.find('button#sign-in').exists()).toBe(true);
    expect(wrapper.find('button#sign-in').text()).toContain('Sign in');
    // Sjekker om registreringsknappen renderes
    expect(wrapper.find('button#create-user-link').exists()).toBe(true);
    expect(wrapper.find('button#create-user-link').text()).toContain('Register');
  });
  it('should disable sign in button when username and password are empty', async () => {
    const wrapper = mount(LoginView);
    expect(wrapper.find('#sign-in').element.disabled).toBeTruthy();
  });

  it('should enable sign in button when username and password are filled', async () => {
    const wrapper = mount(LoginView);
    await wrapper.find('input[type="text"]').setValue('testuser');
    await wrapper.find('input[type="password"]').setValue('password');
    await wrapper.vm.$nextTick();

    expect(wrapper.find('#sign-in').element.disabled).toBeFalsy();
  });

});
