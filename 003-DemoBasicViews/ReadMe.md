# 003-DemoBasicViews
<<<<<<< HEAD

## Ders 31
* Write data from UI to internal memory

```kt
private fun fillUserInfoModel() {
    val name = mEditTextName.text.toString()
    val email = mEditTextEmail.text.toString()
    val username = mEditTextUsername.text.toString()
    val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
    val lastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
    val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

    mUserInfo.also {
        it.name = name
        it.email = email
        it.username = username
        it.maritalStatus = maritalStatus
        it.lastEducationDegree = lastEducationDegree
    }
}

private fun writeUserInfo(bw: BufferedWriter) {
    bw.write("${mUserInfo.username},")
    bw.write("${mUserInfo.name},")
    bw.write("${mUserInfo.email},")
    bw.write("${mUserInfo.maritalStatus},")
    bw.write("${mUserInfo.lastEducationDegree}")

    Toast.makeText(this, R.string.user_info_saved, Toast.LENGTH_SHORT).show()
}

private fun saveAtCloseCallback() {
    try {
        fillUserInfoModel()

        if (mUserInfo.username.isBlank()) {
            Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filesDir, "${mUserInfo.username}.txt")

        if (file.exists()) {
            Log.w(SAVE_REGISTER_INFO, "user already exists")
            Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        BufferedWriter(OutputStreamWriter(openFileOutput("${mUserInfo.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use(::writeUserInfo)  // function/method reference

        Log.i(SAVE_REGISTER_INFO, "Saved")

        finish()
    } catch (ex: IOException) {
        Log.e(SAVE_REGISTER_INFO, ex.message ?: "")
        Toast.makeText(this, R.string.io_problem_occurred_prompt,, Toast.LENGTH_SHORT).show()
    } catch (ex: Exception) {
        Log.e(SAVE_REGISTER_INFO, ex.message, ex)
        Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
    }
}
```

* Read data from internal memory and load them to UI

```kt

/** Read the exist user's information from internal memory
 *
 */
private fun readUserInfo(br: BufferedReader): List<String> {
    val str = br.readLine() ?: throw IOException()  // line(satır) okuma

    // line'ı split etme
    val info = str.split(",") // Java version: str.split("[ ]+")

    return info
}

/** Fill the exist user's information to the UI
 *
 */
private fun fillUI(br: BufferedReader) {
    val existUserInfo = readUserInfo(br)

    mEditTextName.setText(existUserInfo[1])
    mEditTextEmail.setText(existUserInfo[2])
    (mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(existUserInfo[3][0])) as RadioButton).isChecked = true

    mRadioGroupLastEducationDegree.clearCheck()
    val lastEducationDegreeId = existUserInfo[4].toInt()
    if(lastEducationDegreeId != 0)
        (mRadioGroupLastEducationDegree.getChildAt(lastEducationDegreeId - 1) as RadioButton).isChecked = true
}

fun onLoadButtonClicked(view: View) {
    try {
        val username = mEditTextUsername.text.toString()

        if (username.isBlank()) {
            Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filesDir, "$username.txt")

        if (!file.exists()) {
            Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        BufferedReader(InputStreamReader(openFileInput("$username.txt"), StandardCharsets.UTF_8)).use(::fillUI)

        Toast.makeText(this, R.string.user_successfully_loaded_prompt, Toast.LENGTH_SHORT).show()
    } catch (ex: IOException) {
        Log.e(LOAD_REGISTER_INFO, ex.message ?: "")
        Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
    } catch (ex: Exception) {
        Log.e(LOAD_REGISTER_INFO, ex.message, ex)
        Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
    }
}
```

* Açan activity'den (RegisterInfoActivity) açılan activity'e (RegisterPasswordActivity) bilgi gönderme ve bir activity'den (RegisterInfoActivity) başka bir activity (RegisterPasswordActivity) açma:
```kt
Intent(this, RegisterPasswordActivity::class.java).apply { putExtra(REGISTER_INFO, mUserInfo); startActivity(this) }
```

* Activity kapatma:
```kt
fun onCloseButtonClicked(view: View) = finish()
```

* Runtime'da minSDK için versiyon kontrolü `android.os.Build` sınıfının `VERSION.SDK_INT` elemanıytla yapıyoruz. Bu `Int` türdendir:
```kt
mUserInfo = if (android.os.Build.VERSION.SDK_INT < 33 ) intent.getSerializableExtra(REGISTER_INFO) as UserInfoModel else intent.getSerializableExtra(REGISTER_INFO, UserInfoModel::class.java)!!
```


* Uygulamanın file explorer'ında (internal memory'sinde) directory (klasör) oluşturma:
```kt
File(filesDir, "users").mkdirs()
```
> **Not:** Var olan directory yeniden yaratılmaz. Yukarıdaki metotlar var olan bir directory'i ezmez
> 

* Uygulamanın file explorer'ında kendimizin oluşturduğu directory'de file (dosya) oluşturma ve yazma:
```kt
// filesDir'ın altındaki file dizininde users klasöründe (dizininde) dosya oluşturma 
BufferedWriter(OutputStreamWriter(FileOutputStream(File(filesDir,"users/${mUserInfo.username}.txt")), StandardCharsets.UTF_8))
```
```kt
private fun writeUserInfo(bw: BufferedWriter) {
    bw.write("${mUserInfo.username}$DELIMITER")
    bw.write("${mUserInfo.name}$DELIMITER")
    bw.write("${mUserInfo.email}$DELIMITER")
    bw.write("${mUserInfo.maritalStatus}$DELIMITER")
    bw.write("${mUserInfo.lastEducationDegree}")
}

private fun saveData(close: Boolean) {
    // filesDir'ın altındaki file dizininde users klasöründe (dizininde) dosya oluşturma ve yazma
    BufferedWriter(OutputStreamWriter(FileOutputStream(File(filesDir, "users/${mUserInfo.username}.txt")), StandardCharsets.UTF_8)).use(::writeUserInfo)
}
```

* Uygulamanın file explorer'ında kendimizin oluşturduğu directory'deki file'dan okuma:
```kt
val username = mEditTextUsername.text.toString()

// filesDir'ın altındaki file dizininde users klasöründeki dosyadan okuma
BufferedReader(InputStreamReader(FileInputStream(File(filesDir, "users/$username.txt")), StandardCharsets.UTF_8))
```
```kt
private fun fillUI(br: BufferedReader) {
    val existUserInfo = readUserInfo(br)

    mEditTextName.setText(existUserInfo[1])
    mEditTextEmail.setText(existUserInfo[2])
    (mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(existUserInfo[3][0])) as RadioButton).isChecked = true

    mRadioGroupLastEducationDegree.clearCheck()
    val lastEducationDegreeId = existUserInfo[4].toInt()
    if(lastEducationDegreeId != 0)
        (mRadioGroupLastEducationDegree.getChildAt(lastEducationDegreeId - 1) as RadioButton).isChecked = true
}

fun onLoadButtonClicked(view: View) {
    val username = mEditTextUsername.text.toString()

    // filesDir'ın altındaki file dizininde users klasöründeki dosyadan okuma ve UI'ı doldurma
    BufferedReader(InputStreamReader(FileInputStream(File(filesDir, "users/$username.txt")), StandardCharsets.UTF_8)).use(::fillUI)
}
```

* Serialization kullanma
```kt
// Dosya yaratma ve açma
// FileOutputStream ile USERS_FILE_PATH yaratılacak
// FileOutputStream(USERS_FILE_PATH, true) -> true ile append mode'u true'ya çektik böylelikle hep sonuna eklenicek
// ObjectOutputStream object'i Serializable interface'ini implemente etmiş olan bir object'i kendi imzasıla kaydedebiliyor
// ObjectOutputStream object'i ile mUserInfo'yu kaydediyoruz (UserInfoModel, Serializable interface'ini implemente ediyor)
// Biz burda Serializable edilmiş bir objeyi bir Stream üzerine yazdık. Yani objeyi direkt binary olarak yani serialization ile yazdık
ObjectOutputStream(FileOutputStream(File(filesDir, "users/users.dat"), true)).use { it.writeObject(mUserInfo) }
```

* Memory'deki users directory'sinden dosya (file) silme
```kt
File(filesDir, "users/${mUserInfo.username}.txt").delete()
```

* Serializable kullanırak dosyaya kaydedilen verilerin okunması:
Bizim uygulamamızda her bir user info, farklı ObjectOutputStream'ler kullanılarak dosyaya kaydedildiği için bizim de bu verileri okurken farklı ObjectInputStream'ler ile okumamız gerek yani okuma işleminde her adımda ObjectInputStream yaratmamız gerek

```kt
private fun userExistsCallback(fis: FileInputStream): Boolean {
    var result = false

    try {
        // EOFException oluşana kadar döngüye gireceğiz ve her adımda readObject yapıcaz
        // EOFException, dosyanın sonuna gelmek yani EOFException ile dosyanın sonuna gelinip gelinmediği kontrol ediliyor
        // EOFException'nın fırlatılırsa bu dosyanın sonuna gelindi demektir yani okuma bitti demek
        while (true) {
            // Her adımda ObjectInputStream'in yaratılması gerekiyor
            val ois = ObjectInputStream(fis)
            val userInfo = ois.readObject() as UserInfoModel

            if (userInfo.username == mUserInfo.username) { // Eğer koşul doğruysa demek ki bizim user'ımız mevcut demektir yani bu user daha önce kaydedilmiş
                result = true
                break
            }
        }
    } catch (_: EOFException) {
        // Dosyanın sonuna gelindiğinde yani EOFException fırlatıldıdığında dönğü sonlanıcak
    }

    return result
}

private fun userExists(): Boolean {
    var result = false

    try {
        result = FileInputStream(File(filesDir, USERS_FILE_PATH)).use(::userExistsCallback)  // use, içerisindeki ifadenin değerine geri döner
    } catch (ex: IOException) {
        Log.e(USER_INFO_EXIST_LOG_TAG, ex.message ?: "")
        Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
    } catch (ex: Exception) {
        Log.e(USER_INFO_EXIST_LOG_TAG, ex.message, ex)
        Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
    }

    return result
}
```


**NOT:**
```kt
val fis = FileInputStream(File(filesDir, USERS_FILE_PATH))
fis.use { 
    ObjectInputStream(it)  // buradaki it, fis'e karşılık geliyor yani it, FileInputStream türünden
        .use {}   // buradaki it, ObjectInputStream nesnesinin referansıdır yani it, ObjectInputStream türünden
}
```



___
___

## Code snippets examples

> ```kt
> val str = br.readLine()  
> 
> if (str == null)
>     throw IOException()
> ```
> 
> ```kt
> val str = br.readLine() ?: throw IOException()
> ```

<br>

> ```kt
> BufferedWriter(OutputStreamWriter(openFileOutput("${mUserInfoModel.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use { writeUserInfo(it) }
> ```
>
> ```kt
> BufferedWriter(OutputStreamWriter(openFileOutput("${mUserInfo.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use(::writeUserInfo)  // function/method reference
> ```

<br>

> ```kt
>     fun onCloseButtonClicked(view: View) {
>        val dlg = AlertDialog.Builder(this)
>            .setTitle(R.string.alert_dialog_close_title)
>            .setMessage(R.string.alert_dialog_close_message)
>            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveAtCloseCallback() }
>            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
>            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
>            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
>            .create()
>            .show()
>    }
> ```
>
> ```kt
>     fun onCloseButtonClicked(view: View) {
>        val dlg = AlertDialog.Builder(this)
>            .setTitle(R.string.alert_dialog_close_title)
>            .setMessage(R.string.alert_dialog_close_message)
>            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveAtCloseCallback() }
>            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
>            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
>            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
>            .create()
> 
>        dlg.show()
>    }
> ```
>
> ```kt
>     fun onCloseButtonClicked(view: View) {
>        AlertDialog.Builder(this)
>            .setTitle(R.string.alert_dialog_close_title)
>            .setMessage(R.string.alert_dialog_close_message)
>            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveAtCloseCallback() }
>            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
>            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
>            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
>            .create()
>            .show()
>    }
> ```

<br>

> ```kt
>         
> for (i in 0..<mRadioGroupMaritalStatus.childCount)
>   mRadioGroupMaritalStatus.getChildAt(i).tag = MARITAL_STATUS_TAGS[i]
> ```
>
> ```kt
> (0..<mRadioGroupMaritalStatus.childCount).forEach { mRadioGroupMaritalStatus.getChildAt(it).tag = MARITAL_STATUS_TAGS[it] }
> ```

<br>

> ```kt
> ```
>
> ```kt
> ```



<br>

> ```kt
> ```
>
> ```kt
> ```

<br>

> ```kt
> ```
>
> ```kt
> ```



<br>

> ```kt
> ```
>
> ```kt
> ```

<br>

> ```kt
> ```
>
> ```kt
> ```



<br>

> ```kt
> ```
>
> ```kt
> ```

<br>

> ```kt
> ```
>
> ```kt
> ```




























=======
>>>>>>> parent of 2e71286 (Updated ReadMe file)
