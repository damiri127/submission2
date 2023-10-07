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

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username")  username: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")  username: String
    ): Call<List<ItemsItem>>
}