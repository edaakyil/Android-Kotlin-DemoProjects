package com.edaakyil.android.kotlin.app.sqlite.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edaakyil.android.kotlin.app.sqlite.dao.IProductDao
import com.edaakyil.android.kotlin.app.sqlite.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class PaymentLocalDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
}

// PaymentLocalDatabase sınıfı türünden nesnenin referansıyla productDao metodunu çağırdığımızda
// IProductDoa arayüzünün metotlarını implemente etmiş olan PaymentLocalRepositoryHelper nesnesinin
// adresini alıcaz böylelikle insert dediğimizde PaymentLocalRepositoryHelper sınıfının insert metodu çalışmış olacak