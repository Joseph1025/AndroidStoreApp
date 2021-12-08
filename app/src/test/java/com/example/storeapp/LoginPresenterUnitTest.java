package com.example.storeapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterUnitTest {
    @Mock
    LoginScreen view;

    @Mock
    LoginModel model;

    @Test
    public void testPresenter(){
        when(view.getUserName()).thenReturn("mockUser");
        when(view.getPassWord()).thenReturn("1234");

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.userLogin("Customer");
        verify(model).userLogin("mockUser", "1234", "Customer", view);

        presenter.userLogin("Store Owner");
        verify(model).userLogin("mockUser", "1234", "Store Owner", view);

        LoginPresenter.loginFailed(view);
        verify(view).displayMessage("wrong username/password, please try again");

        LoginPresenter.loginSuccess("Customer", view);
        verify(view).navigateToCusPage();

        LoginPresenter.loginSuccess("Store Owner", view);
        verify(view).navigateToStrPage();

        when(view.getUserName()).thenReturn("");
        when(view.getPassWord()).thenReturn("");
        presenter.userLogin("Customer");
        verify(view).displayMessage("username/password cannot be empty");
    }
}