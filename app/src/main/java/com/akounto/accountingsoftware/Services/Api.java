package com.akounto.accountingsoftware.Services;

import com.akounto.accountingsoftware.Data.BankLinkData;
import com.akounto.accountingsoftware.Data.CheckEmailData;
import com.akounto.accountingsoftware.Data.Dashboard.DashboardData;
import com.akounto.accountingsoftware.Data.DashboardSearchData.SearchDashboardData;
import com.akounto.accountingsoftware.Data.LoginData;
import com.akounto.accountingsoftware.Data.Profile.UserRegister;
import com.akounto.accountingsoftware.Data.RegisterBank.AutoSynData;
import com.akounto.accountingsoftware.Data.RegisterBank.BankAccountData;
import com.akounto.accountingsoftware.Data.SoclData;
import com.akounto.accountingsoftware.model.ForgotPasswordData;
import com.akounto.accountingsoftware.response.SignUp.SignUpResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("token")
    @FormUrlEncoded
    Call<LoginData> loginRequest(@Header("X-Signature") String header, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

    @POST("api/bank/link-token")
    @FormUrlEncoded
    Call<BankLinkData> bankLinkRequest(@Header("X-Signature") String signature, @Header("X-Company") String header, @Header("Authorization") String authHeader, @Field("") String dummy);

    @POST("api/bank/register-bank")
    @FormUrlEncoded
    Call<BankAccountData> registerBankRequest(@Header("X-Signature") String signature, @Header("X-Company") String company, @Header("Authorization") String authHeader, @Field("PublicToken") String public_token, @Field("InstitutionId") String institution_id, @Field("InstitutionName") String institution_name);

    @GET("api/bank/get")
    Call<BankAccountData> getBankRequest(@Header("X-Signature") String signature, @Header("X-Company") String header, @Header("Authorization") String authHeader, @Query("") String dummy);

    @POST("api/bank/auto-import-set")
    @FormUrlEncoded
    Call<AutoSynData> autoImportSetRequest(@Header("X-Signature") String signature, @Header("X-Company") String header, @Header("Authorization") String authHeader, @Field("BankId") String BankId, @Field("AccountId") String AccountId, @Field("IsAutoImport") boolean IsAutoImport);

    @POST("api/bank/import-transaction")
    @FormUrlEncoded
    Call<BankAccountData> importBankTransactionRequest(@Header("X-Signature") String signature, @Header("X-Company") String company, @Header("Authorization") String authHeader, @Field("BankId") String BankId);

    @POST("api/profile/check-email-exist")
    @FormUrlEncoded
    Call<CheckEmailData> checkEmailExistRequest(@Header("X-Signature") String signature, @Field("Email") String email);

    @POST("api/profile/native-app-register-social-account")
    Call<SoclData> externalRegister(@Header("X-Signature") String signature, @Body JsonObject request);

    @POST("api/profile/native-app-register-social-account")
    Call<UserRegister> userRegister(@Header("X-Signature") String signature, @Body JsonObject request);

    @GET("api/profile/native-app-social-login")
    Call<SoclData> extLogin(@Header("X-Signature") String signature, @Query("provider") String provider, @Query("idToken") String externalAccessToken);

    @POST("api/accounting/dashboad")
    Call<DashboardData> getDashboard(@Header("X-Signature") String signature, @Header("X-Company") String company, @Header("Authorization") String authHeader, @Body JsonObject request);

    @GET("api/report/search-dashboard")
    Call<SearchDashboardData> searchDashboard(@Header("X-Signature") String signature, @Header("Authorization") String authorization, @Header("X-Company") String x_company);

    @POST("api/profile/forgot-user-password")
    Call<ForgotPasswordData> fogotPasswordRequest(@Header("X-Signature") String signature, @Body JsonObject email);

    @POST("api/profile/register-business-new")
    Call<SignUpResponse> register(@Header("X-Signature") String signature, @Body JsonObject request);

}
