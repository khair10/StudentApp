package com.khair.appforitis.data.repositoryimpl.temporary

import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkProfile
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlin.collections.ArrayList

class ArrayListProfileRepository: Repository<Profile> {

    companion object {
        var counter = 14
        val networkProfiles: List<NetworkProfile> = ArrayList<NetworkProfile>().apply {
            val tempStudentName = "Ключников Илья"
            val tempCompanyName = "Citilink"
            val tempPhone = "89298232323"
            val tempVk = "vk.com/id43434343"
            val tempTelegram = "@tele_monster"
            val tempFacebook = "facebook.com/43434343"
            val tempAdditionalDescription = "Я люблю солнечную погоду, люблю лето, летом можно купаться в реке, а я не могу жить далеко от воды."
            val beginIndex = 0
            val endIndex = 12

            for (i in beginIndex..endIndex){
                add(
                    NetworkProfile(
                        i.toLong(),
                        tempStudentName,
                        NetworkCompanyItemDto(0L, tempCompanyName),
                        tempPhone,
                        tempVk,
                        tempTelegram,
                        tempFacebook,
                        tempAdditionalDescription
                    )
                )
            }
            add(
                NetworkProfile(
                    13L,
                    "Варинов Стас",
                    NetworkCompanyItemDto(0L, "Tehno"),
                    tempPhone,
                    tempVk,
                    tempTelegram,
                    tempFacebook,
                    tempAdditionalDescription
                )
            )
        }
    }

    override fun get(id: Long): Flowable<Profile> {
        val sourceProfile = networkProfiles[id.toInt()]
        return Flowable.just(
            Profile(
                sourceProfile.id,
                sourceProfile.name,
                CompanyItem(sourceProfile.company!!.id, sourceProfile.company!!.name),
                sourceProfile.phone,
                sourceProfile.vk,
                sourceProfile.telegram,
                sourceProfile.facebook,
                sourceProfile.additionalInformation
            )
        )
    }

    override fun getAll(): Flowable<List<Profile>> {
        return Flowable.just(networkProfiles.map {
               Profile(
                    it.id,
                    it.name,
                    CompanyItem(it.company!!.id, it.company!!.name),
                    it.phone,
                    it.vk,
                    it.telegram,
                    it.facebook,
                    it.additionalInformation
                )
            }
        )
    }

    override fun add(item: Profile): Completable {
        (networkProfiles as ArrayList)[item.id.toInt()] = NetworkProfile(
            item.id,
            item.name,
            NetworkCompanyItemDto(item.company.id, item.company.name),
            item.phone,
            item.vk,
            item.telegram,
            item.facebook,
            item.additionalInformation
        )
        ++counter
        return Completable.complete()
    }

    override fun get(): Flowable<Profile> {
        val sourceProfile = networkProfiles[0]
        return Flowable.just(
            Profile(
                sourceProfile.id,
                sourceProfile.name,
                CompanyItem(sourceProfile.company!!.id, sourceProfile.company!!.name),
                sourceProfile.phone,
                sourceProfile.vk,
                sourceProfile.telegram,
                sourceProfile.facebook,
                sourceProfile.additionalInformation
            )
        )
    }
}