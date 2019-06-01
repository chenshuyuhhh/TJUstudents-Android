package com.chenshuyusc.tjustudents.Manage.Service

import com.chenshuyusc.tjustudents.Entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal const val SELECTION = "selections"
internal const val COURSE = "courses"
internal const val STUDENT = "students"

interface RetrofitService {

    /**
     * SELECTION
     */

    @GET("${SELECTION}/list")
    fun getSelectionPage(@Query("page") page: Int, @Query("size") size: Int): Deferred<SelectionPage>

    @POST("${SELECTION}/update")
    fun updateSelection(@Query("selection") selection: String): Deferred<sidSelection>

    @POST("${SELECTION}/add")
    fun addSelection(@Query("selection") selections: String): Deferred<sidSelection>

    @POST("${SELECTION}/deleteOne")
    fun deleteSelection(@Query("selection") selection: String): Deferred<sidSelection>

    @GET("${SELECTION}/sid")
    fun getSelectionBySid(@Query("id") id: String): Deferred<sidSelection>

    @GET("${SELECTION}/sidcid")
    fun sidcid(@Query("sid") sid: String, @Query("cid") cid: String): Deferred<sidSelection>

    @GET("${SELECTION}/sidcname")
    fun sidcname(@Query("sid") sid: String, @Query("cname") cname: String): Deferred<sidSelection>

    @GET("${SELECTION}/snamecid")
    fun snamecid(@Query("sname") sname: String, @Query("cid") cid: String): Deferred<sidSelection>

    @GET("${SELECTION}/snamecname")
    fun snamecname(@Query("sname") sname: String, @Query("cname") cname: String): Deferred<sidSelection>

    @GET("${SELECTION}/cid")
    fun getSelectionByCid(@Query("id") id: String): Deferred<selectionsCid>

    @GET("${SELECTION}/cname")
    fun getSelectionByCname(@Query("name") name: String): Deferred<selectionsCid>

    /**
     * COURSE
     */

    @GET("${COURSE}/list")
    fun getCoursePage(@Query("page") page: Int, @Query("size") size: Int): Deferred<CoursePage<DataC>>

    @GET("${COURSE}/detail")
    fun getCourseByID(@Query("id") id: String): Deferred<CoursePage<Course>>

    @GET("${COURSE}/detailname")
    fun getCourseByName(@Query("name") name: String): Deferred<CoursePage<Course>>

    @POST("${COURSE}/delete")
    fun deleteCourse(@Query("id") id: String): Deferred<CoursePage<Course>>

    @POST("${COURSE}/update")
    fun updateCourse(@Query("course") course: String): Deferred<SelectionPage>

    @POST("${COURSE}/add")
    fun addCourse(@Query("course") course: String): Deferred<SelectionPage>


    @GET("${COURSE}/score")
    fun getscore(@Query("cid") cid: String): Deferred<CoursePage<CourseInfo>>

    /**
     * STUDENTS
     */

    @GET("${STUDENT}/list")
    fun getStudentPage(@Query("page") page: Int, @Query("size") size: Int): Deferred<StudentPage<DataS>>

    @GET("${STUDENT}/studentById")
    fun getStudentById(@Query("id") id: String): Deferred<StudentSearchID>

    @GET("${STUDENT}/studentByName")
    fun getStudentByName(@Query("name") name: String): Deferred<StudentSearchName>

    @POST("${STUDENT}/delete")
    fun deleteStu(@Query("id") id: String): Deferred<StudentPage<DataS>>

    @POST("${STUDENT}/add")
    fun addStudent(@Query("student") student: String): Deferred<StudentSearchID>

    @POST("${STUDENT}/update")
    fun updateStudent(@Query("student") student: String): Deferred<StudentSearchID>

    @GET("${STUDENT}/classAvg")
    fun classAvg(@Query("classname") classname: String, @Query("year") year: Int): Deferred<StudentPage<List<StudentInfo>>>
}