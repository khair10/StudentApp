package com.khair.appforitis.data.repositoryimpl.temporary

import android.util.Log
import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkStudentItemDto
import com.khair.appforitis.data.model.NetworkVacancy
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import kotlin.collections.ArrayList

class ArrayListVacancyRepository: Repository<Vacancy>{

    companion object {
        var counter = 14
        val networkVacancies: List<NetworkVacancy> = ArrayList<NetworkVacancy>().apply{
            val tempName = "Android разработчик"
            val tempDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at elementum orci, non elementum diam. Nullam accumsan finibus elit, iaculis volutpat diam aliquam in. Vivamus ut euismod arcu, eget malesuada metus. Etiam purus massa, tincidunt non sem sit amet, vehicula tincidunt leo. Nullam tellus purus, vestibulum vitae orci placerat, sodales mattis lacus. Mauris convallis turpis ultrices, aliquam massa eu, rhoncus urna. Nunc eleifend, purus in mollis dapibus, lorem dui dictum ligula, id porta enim quam non est. Morbi et mi id eros posuere ornare. Sed lobortis nulla eget efficitur mollis. In hac habitasse platea dictumst. Suspendisse non massa fringilla, accumsan nulla et, ornare ligula. Nam sollicitudin risus quis dignissim molestie. Duis imperdiet ligula eget massa tristique, non scelerisque ligula lacinia. Nam ac eros venenatis justo dictum hendrerit a quis sapien. In magna lorem, suscipit sed urna et, sagittis pharetra lectus. Nullam id tellus at lorem faucibus ultricies a id enim."
            val tempCompanyName = "Citilink"
            val tempRating = 3.0F
            val tempRecallsCount = 23
            val tempSalary = 30000
            val beginIndex = 0
            val endIndex = 12
            val date = Date()

            for (i in beginIndex..endIndex){
                add(
                    NetworkVacancy(
                        i.toLong(),
                        tempName,
                        tempDescription,
                        NetworkCompanyItemDto(0L, tempCompanyName),
                        tempRating,
                        tempRecallsCount,
                        tempSalary,
                        NetworkStudentItemDto(0L, "Ключников Илья"),
                        date.addDays(i).time
                    )
                )
            }
            add(
                NetworkVacancy(
                    13L,
                    "Java разработчик",
                    tempDescription,
                    NetworkCompanyItemDto(13L, "Tehno"),
                    tempRating,
                    tempRecallsCount,
                    tempSalary,
                    NetworkStudentItemDto(13L, "Варинов Стас"),
                    date.addDays(13).time
                )
            )
        }
    }

    override fun get(id: Long): Flowable<Vacancy> {
        val sourceVacancy = networkVacancies[id.toInt()]
        return Flowable.just(
            Vacancy(
                sourceVacancy.id,
                sourceVacancy.name,
                sourceVacancy.information,
                CompanyItem(sourceVacancy.company.id, sourceVacancy.company.name),
                sourceVacancy.rating,
                sourceVacancy.recallsCount,
                sourceVacancy.salary,
                StudentItem(sourceVacancy.student.id, sourceVacancy.student.name),
                Date(sourceVacancy.date)
            )
        )
    }

    override fun getAll(): Flowable<List<Vacancy>> {
        return Flowable.just(networkVacancies.map {
                Vacancy(
                    it.id,
                    it.name,
                    it.information,
                    CompanyItem(it.company.id, it.company.name),
                    it.rating,
                    it.recallsCount,
                    it.salary,
                    StudentItem(it.student.id, it.student.name),
                    Date(it.date)
                )
            }
        )
    }

    override fun add(item: Vacancy): Completable {
        (networkVacancies as ArrayList).add(
            NetworkVacancy(
                counter.toLong(),
                item.name,
                item.information,
                NetworkCompanyItemDto(item.company.id, item.company.name),
                item.rating,
                item.recallsCount,
                item.salary,
                NetworkStudentItemDto(item.student.id, item.student.name),
                item.date.time
            )
        )
        ++counter
        return Completable.complete()
    }

    override fun get(): Flowable<Vacancy> {
        val sourceVacancy = networkVacancies[0]
        return Flowable.just(
            Vacancy(
                sourceVacancy.id,
                sourceVacancy.name,
                sourceVacancy.information,
                CompanyItem(sourceVacancy.company.id, sourceVacancy.company.name),
                sourceVacancy.rating,
                sourceVacancy.recallsCount,
                sourceVacancy.salary,
                StudentItem(sourceVacancy.student.id, sourceVacancy.student.name),
                Date(sourceVacancy.date)
            )
        )
    }
}