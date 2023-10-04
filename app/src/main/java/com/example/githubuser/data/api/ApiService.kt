package com.example.githubuser.data.api
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.GithubResponse
import com.example.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    // endpoint
    @GET("search/users")
    fun getGithubUser(
        @Query("q") query:String
    ): Call<GithubResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    //@Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username")  username: String
    ): Call<List<ItemsItem>>

    //@Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")  username: String
    ): Call<List<ItemsItem>>
}