package com.devik.homebarorder.data.source.remote

import com.devik.homebarorder.data.model.CartMenuItem
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.util.Constants
import com.devik.homebarorder.util.DataFormatUtil
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val postgrest: Postgrest,
    private val moshi: Moshi,
    private val preferenceManager: PreferenceManager
) : OrderDataSource {

    private fun fromOrderList(order: List<CartMenuItem>): String {
        val listType = Types.newParameterizedType(List::class.java, CartMenuItem::class.java)
        val adapter: JsonAdapter<List<CartMenuItem>> = moshi.adapter(listType)
        return adapter.toJson(order)
    }

    override suspend fun insertOrder(order: List<CartMenuItem>): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from(Constants.SUPABASE_DB_TABLE_NAME).insert(
                    mapOf(
                        Constants.SUPABASE_DB_COLUMN_CREATE_AT to DataFormatUtil.getCurrentTime(),
                        Constants.SUPABASE_DB_COLUMN_ORDER to fromOrderList(order),
                        Constants.SUPABASE_DB_COLUMN_ORDER_USER_MAIL to preferenceManager.getString(
                            Constants.KEY_MAIL_ADDRESS,
                            ""
                        ),
                        Constants.SUPABASE_DB_COLUMN_ORDER_NUMBER to "${
                            preferenceManager.getInt(
                                Constants.KEY_ORDER_NUMBER
                            ) + 1
                        }",
                    )
                )
                if(preferenceManager.getInt(Constants.KEY_ORDER_NUMBER) == 1000){
                    preferenceManager.putInt(Constants.KEY_ORDER_NUMBER, 0)
                }else{
                    preferenceManager.putInt(Constants.KEY_ORDER_NUMBER, preferenceManager.getInt(Constants.KEY_ORDER_NUMBER)+1)
                }
                true
            }
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }
}