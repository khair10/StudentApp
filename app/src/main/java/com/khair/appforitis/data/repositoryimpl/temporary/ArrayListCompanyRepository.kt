package com.khair.appforitis.data.repositoryimpl.temporary

import com.khair.appforitis.data.model.NetworkCompany
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository.Companion.networkCompanies
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

class ArrayListCompanyRepository: Repository<Company> {

    companion object {
        var counter = 14
        val networkCompanies: List<NetworkCompany> = ArrayList<NetworkCompany>().apply {
            val tempCompanyName = "Citilink"
            val tempCompanyAddress = "Lenina 26"
            val tempCompanySite = "www.lenina26.com"
            val tempCompanyPhone = "+7 934 563-34-34"
            val tempCompanyDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at elementum orci, non elementum diam. Nullam accumsan finibus elit, iaculis volutpat diam aliquam in. Vivamus ut euismod arcu, eget malesuada metus. Etiam purus massa, tincidunt non sem sit amet, vehicula tincidunt leo. Nullam tellus purus, vestibulum vitae orci placerat, sodales mattis lacus. Mauris convallis turpis ultrices, aliquam massa eu, rhoncus urna. Nunc eleifend, purus in mollis dapibus, lorem dui dictum ligula, id porta enim quam non est. Morbi et mi id eros posuere ornare. Sed lobortis nulla eget efficitur mollis. In hac habitasse platea dictumst. Suspendisse non massa fringilla, accumsan nulla et, ornare ligula. Nam sollicitudin risus quis dignissim molestie. Duis imperdiet ligula eget massa tristique, non scelerisque ligula lacinia. Nam ac eros venenatis justo dictum hendrerit a quis sapien. In magna lorem, suscipit sed urna et, sagittis pharetra lectus. Nullam id tellus at lorem faucibus ultricies a id enim."
            val tempCompanyRating = 3.0F
            val tempCompanyRecallsCount = 23
            val beginIndex = 0
            val endIndex = 12

            for(i in beginIndex..endIndex){
                add(
                    NetworkCompany(
                        i.toLong(),
                        tempCompanyName,
                        tempCompanyAddress,
                        tempCompanySite,
                        tempCompanyPhone,
                        tempCompanyDescription,
                        tempCompanyRating,
                        tempCompanyRecallsCount
                    )
                )
            }
            add(
                NetworkCompany(
                    13L,
                    "Tehno",
                    tempCompanyAddress,
                    tempCompanySite,
                    tempCompanyPhone,
                    tempCompanyDescription,
                    tempCompanyRating,
                    tempCompanyRecallsCount
                )
            )
        }
    }

    override fun get(id: Long): Flowable<Company> {
        val sourceCompany = networkCompanies[id.toInt()]
        return Flowable.just(
            Company(
                sourceCompany.id,
                sourceCompany.name,
                sourceCompany.address,
                sourceCompany.website,
                sourceCompany.phone,
                sourceCompany.information,
                sourceCompany.rating,
                sourceCompany.recallsCount
            )
        )
    }

    override fun getAll(): Flowable<List<Company>> {
        return Flowable.just(
            networkCompanies.map {
                Company(
                    it.id,
                    it.name,
                    it.address,
                    it.website,
                    it.phone,
                    it.information,
                    it.rating,
                    it.recallsCount
                )
            }
        )
    }

    override fun add(item: Company): Completable {
        (networkCompanies as ArrayList).add(
            NetworkCompany(
                counter.toLong(),
                item.name,
                item.address,
                item.website,
                item.phone,
                item.information,
                0F,
                0
            )
        )
        ++counter
        return Completable.complete()
    }

    override fun get(): Flowable<Company> {
        val sourceCompany = networkCompanies[0]
        return Flowable.just(
            Company(
                sourceCompany.id,
                sourceCompany.name,
                sourceCompany.address,
                sourceCompany.website,
                sourceCompany.phone,
                sourceCompany.information,
                sourceCompany.rating,
                sourceCompany.recallsCount
            )
        )
    }
}