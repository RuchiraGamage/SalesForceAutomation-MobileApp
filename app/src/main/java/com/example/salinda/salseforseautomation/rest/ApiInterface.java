package com.example.salinda.salseforseautomation.rest;

import com.example.salinda.salseforseautomation.Message.Chat.UserModel;
import com.example.salinda.salseforseautomation.mapActivity.LocationModel;
import com.example.salinda.salseforseautomation.model.ExpensesModel;
import com.example.salinda.salseforseautomation.model.LeaveHistryModel;
import com.example.salinda.salseforseautomation.model.LeaveModel;
import com.example.salinda.salseforseautomation.model.LoginModel;
import com.example.salinda.salseforseautomation.model.MonthReportModel;
import com.example.salinda.salseforseautomation.model.MonthlyReportModel;
import com.example.salinda.salseforseautomation.model.OutletModel;
import com.example.salinda.salseforseautomation.model.OutstandingModel;
import com.example.salinda.salseforseautomation.model.PreOrderPostModel;
import com.example.salinda.salseforseautomation.model.ProductsModel;
import com.example.salinda.salseforseautomation.model.RouteModel;
import com.example.salinda.salseforseautomation.model.VanOrderPostModel;
import com.example.salinda.salseforseautomation.model.VanProductModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/login")
    Call<LoginModel> getLogin(@Query("Username") String userName, @Query("password") String password);
    @GET("api/Product")
    Call<List<ProductsModel>> getProduct();
    @GET("api/Outlets")
    Call<List<OutletModel>> getOutlet();
    @GET("api/UsersTodayOutlets/{id}")
    Call<List<OutletModel>> getOutletsByUserId(@Path("id") int id);
    @GET("api/LatestOutstandingDetails/{Id}")
    Call<Float> getLatestOutstanding(@Path("Id") int Id);
    @POST("api/Order")
    Call<ResponseBody> postPreOrderPayment(@Body PreOrderPostModel preOrderPostModel);
    @POST("api/VanOrder")
    Call<ResponseBody> postVanOrderPayment(@Body VanOrderPostModel vanOrderPostModel);
    @GET("api/UsersMonthlySummary/{id}")
    Call<List<MonthlyReportModel>> getUsersMonthlyReport(@Path("id") int id);
    @GET("api/OutletsMonthlySummary/{id}")
    Call<List<MonthlyReportModel>> getOutletsMonthlyReport(@Path("id") int id);
    @GET("api/RoutesMonthlySummary/{id}")
    Call<List<MonthlyReportModel>> getRoutesMonthlyReport(@Path("id") int id);
    @GET("api/Outstanding")
    Call<List<OutstandingModel>> getOutstanding();
    @POST("api/Expenses")
    Call<ResponseBody> postExpenses(@Body ExpensesModel expensesModel);
    @POST("api/Leave")
    Call<ResponseBody> postLeave(@Body LeaveModel leaveModel);
    @GET("api/Leave/{Id}")
    Call<List<LeaveHistryModel>> getMyLeaves(@Path("Id") int Id);
    @GET("api/Route")
    Call<List<RouteModel>> getAllRoute();
    @GET("api/UsersSales")
    Call<List<MonthReportModel>> getUserMonthSales(@Query("userId") int userId, @Query("month") int month, @Query("year") int year);
    @GET("api/OutletsSales")
    Call<List<MonthReportModel>> getOutletMonthSales(@Query("userId") int userId, @Query("month") int month, @Query("year") int year);
    @GET("api/OutletsOfTheRoute/{Id}")
    Call<List<LocationModel>> getOutletOfTheRoute(@Path("Id") int Id);
    @GET("api/OrderedProducts/{Id}")
    Call<List<ProductsModel>> getOrderedProduct(@Path("Id") int Id);
    @GET("api/UsersVanProducts")
    Call<List<VanProductModel>> getVanProduct(@Query("UserId") int Id);
    @GET("api/Users")
    Call<List<UserModel>> getUsers();
}
