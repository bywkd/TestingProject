package com.example.companyproject.model

import com.google.gson.annotations.SerializedName

class SearchImage {

    @SerializedName("meta")
    var meta: Meta? = null

    @SerializedName("documents")
    var documents: ArrayList<Document>? = null

    inner class Meta {
        /*검색어에 검색된 문서수*/
        @SerializedName("total_count")
        var totalCount: Int = 0

        /*total count 중에 노출 가능 문서수*/
        @SerializedName("pageable_count")
        var pageableCount: Int = 0

        /*is_end 현재 페이지가 마지막페이지 인지 여부*/
        @SerializedName("is_end")
        var isEnd: Boolean = true
    }

    inner class Document {
        /*컬렉션*/
        @SerializedName("collection")
        var collection: String? = null

        /*이미지 썸네일 URL*/
        @SerializedName("thumbnail_url")
        var thumbnailUrl: String? = null

        /*이미지 URL*/
        @SerializedName("image_url")
        var imageUrl: String? = null

        /*이미지의 가로크기*/
        @SerializedName("width")
        var width: Int = 0

        /*이미지의 세로크기*/
        @SerializedName("height")
        var height: Int = 0

        /*출처명*/
        @SerializedName("display_sitename")
        var displaySitename: String? = null

        /*문서 URL*/
        @SerializedName("doc_url")
        var docUrl: String? = null

        /*문서 작성시간. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]*/
        @SerializedName("datetime")
        var datetime: String? = null

    }
}