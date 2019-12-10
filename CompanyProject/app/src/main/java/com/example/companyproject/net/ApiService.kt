package com.example.companyproject.net

import com.example.companyproject.model.SearchImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*
    *@param query 검색을 원하는 질의어
    *@param sort 결과 문서 정렬 방식	X (accuracy)	accuracy (정확도순) or recency (최신순)
    *@param page 결과 페이지 번호	X(기본 1)	1-50 사이 Integer
    *@param size 한 페이지에 보여질 문서의 개수	X(기본 80)	1-80 사이 Integer
    * */
    @GET("/v2/search/image")
    fun listEvents(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80
    ): Call<SearchImage>
}