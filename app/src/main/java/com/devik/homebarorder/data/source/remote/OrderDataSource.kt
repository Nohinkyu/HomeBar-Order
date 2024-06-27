package com.devik.homebarorder.data.source.remote

import com.devik.homebarorder.data.model.CartMenuItem

interface OrderDataSource {

    suspend fun insertOrder(order: List<CartMenuItem>):Boolean
}