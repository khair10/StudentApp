package com.khair.appforitis.domain.interactor

import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.presenter.PresenterProtocol
import com.khair.appforitis.domain.repository.Repository

class GetCompanyInteractor(val id: Long): Interactor {

    private lateinit var companyRepository: Repository<Company>
    private lateinit var presenterProtocol: PresenterProtocol<Company>

    override fun execute() {
//        val company = companyRepository.get(id)
//        presenterProtocol.showCompany(company)
    }
}