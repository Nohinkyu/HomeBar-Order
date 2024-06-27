package com.devik.homebarorder.data.repository

import com.devik.homebarorder.data.model.CartMenuItem
import com.devik.homebarorder.data.source.remote.OrderDataSource
import javax.inject.Inject

class RemoteOrderRepository @Inject constructor(private val orderDataSource: OrderDataSource) {

    suspend fun insertOrder(order: List<CartMenuItem>): Boolean {
        return orderDataSource.insertOrder(order)
    }
}