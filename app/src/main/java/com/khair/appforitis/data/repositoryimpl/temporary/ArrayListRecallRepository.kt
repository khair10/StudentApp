package com.khair.appforitis.data.repositoryimpl.temporary

import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkRecall
import com.khair.appforitis.data.model.NetworkStudentItemDto
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import kotlin.collections.ArrayList

class ArrayListRecallRepository: Repository<Recall> {

    companion object {
        var counter = 14
        val networkRecalls: List<NetworkRecall> = ArrayList<NetworkRecall>().apply {
            val tempStudentName = "Ключников Илья"
            val tempDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at elementum orci, non elementum diam. Nullam accumsan finibus elit, iaculis volutpat diam aliquam in. Vivamus ut euismod arcu, eget malesuada metus. Etiam purus massa, tincidunt non sem sit amet, vehicula tincidunt leo. Nullam tellus purus, vestibulum vitae orci placerat, sodales mattis lacus. Mauris convallis turpis ultrices, aliquam massa eu, rhoncus urna. Nunc eleifend, purus in mollis dapibus, lorem dui dictum ligula, id porta enim quam non est. Morbi et mi id eros posuere ornare. Sed lobortis nulla eget efficitur mollis. In hac habitasse platea dictumst. Suspendisse non massa fringilla, accumsan nulla et, ornare ligula. Nam sollicitudin risus quis dignissim molestie. Duis imperdiet ligula eget massa tristique, non scelerisque ligula lacinia. Nam ac eros venenatis justo dictum hendrerit a quis sapien. In magna lorem, suscipit sed urna et, sagittis pharetra lectus. Nullam id tellus at lorem faucibus ultricies a id enim."
            val tempCompanyName = "Citilink"
            val tempRating = 4.0F
            val beginIndex = 0
            val endIndex = 12
            val date = Date()

            for (i in beginIndex..endIndex){
                add(
                    NetworkRecall(
                        i.toLong(),
                        NetworkStudentItemDto(0L, tempStudentName),
                        tempDescription,
                        NetworkCompanyItemDto(0L, tempCompanyName),
                        tempRating,
                        date.addDays(i).time
                    )
                )
            }
            add(
                NetworkRecall(
                    13L,
                    NetworkStudentItemDto(13L, "Варинов Стас"),
                    tempDescription,
                    NetworkCompanyItemDto(13L, "Tehno"),
                    tempRating,
                    date.addDays(13).time
                )
            )
        }
    }

    override fun get(id: Long): Flowable<Recall> {
        val sourceRecall = networkRecalls[id.toInt()]
        return Flowable.just(
            Recall(
                sourceRecall.id,
                StudentItem(sourceRecall.student!!.id, sourceRecall.student!!.name),
                sourceRecall.information,
                CompanyItem(sourceRecall.company!!.id, sourceRecall.company!!.name),
                sourceRecall.rating,
                Date(sourceRecall.date)
            )
        )
    }

    override fun getAll(): Flowable<List<Recall>> {
        return Flowable.just(
            networkRecalls.map {
                Recall(
                    it.id,
                    StudentItem(it.student!!.id, it.student!!.name),
                    it.information,
                    CompanyItem(it.company!!.id, it.company!!.name),
                    it.rating,
                    Date(it.date)
                )
            }
        )
    }

    override fun add(item: Recall): Completable {
        (networkRecalls as ArrayList).add(
            NetworkRecall(
                counter.toLong(),
                NetworkStudentItemDto(item.student.id, item.student.name),
                item.information,
                NetworkCompanyItemDto(item.company.id, item.company.name),
                item.rating,
                item.date.time
            )
        )
        ++counter
        return Completable.complete()
    }

    override fun get(): Flowable<Recall> {
        val sourceRecall = networkRecalls[0]
        return Flowable.just(
            Recall(
                sourceRecall.id,
                StudentItem(sourceRecall.student!!.id, sourceRecall.student!!.name),
                sourceRecall.information,
                CompanyItem(sourceRecall.company!!.id, sourceRecall.company!!.name),
                sourceRecall.rating,
                Date(sourceRecall.date)
            )
        )
    }
}