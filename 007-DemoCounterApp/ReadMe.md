# 007-DemoCounterApp

* di -> dependency injection

## Code snippets examples

<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
if (mStartedFlag)
    mBinding.startStopButtonText = resources.getString(R.string.start_text)
else
    mBinding.startStopButtonText = resources.getString(R.string.stop_text)
```

```kt
mBinding.startStopButtonText = if (mStartedFlag) resources.getString(R.string.start_text) else resources.getString(R.string.stop_text)
```

```kt
mBinding.startStopButtonText = resources.getString(if (mStartedFlag) R.string.start_text else R.string.stop_text)
```

</div>
<div style="background-color:#f6f6f9;padding:10px 20px;margin:2em 0;border:solid #eee 1px;">

```kt
//mBinding.mainActivityTextViewCounter.text = "%02d/%02d/%02d".format(hour, minute, second)
mBinding.mainActivityTextViewCounter.text = "$hour:$minute:$second"
```

```kt
mBinding.mainActivityTextViewCounter.setText("$hour:$minute:$second")
```

```kt
"$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this }
```

> ```xml
> <string name="deneme">%1$d:%2$d:%3$d</string>
> ```
> 
> ```kt
> mBinding.mainActivityTextViewCounter.text = getString(R.string.deneme, hour, minute, second)
> ```

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