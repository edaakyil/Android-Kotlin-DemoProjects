
# 003-DemoBasicViews

## Ders 34



___
___

## Code snippets examples

<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy().apply { username = "" }
```

```kt
mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy(username = "")
```
</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
mBinding.username = resources.getString(R.string.username, intent.getStringExtra(USERNAME) ?: resources.getString(R.string.anonymous))
mBinding.password = resources.getString(R.string.password, intent.getStringExtra(PASSWORD) ?: resources.getString(R.string.anonymous))
```

```kt
mBinding.username = "Username: " + (intent.getStringExtra(USERNAME) ?: resources.getString(R.string.anonymous))
mBinding.password = "Password: " + (intent.getStringExtra(PASSWORD) ?: resources.getString(R.string.anonymous))
```

</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
```

```kt
```

</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
```

```kt
```

</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
```

```kt
```

</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
```

```kt
```

</div>





























