<#macro passwordField id label inputSize labelSize=2 name=id>
    <div class="form-group">
        <label for="${id}" class="col-sm-${labelSize} control-label">
            ${label}
        </label>
        <div class="col-sm-${inputSize} input-img password-visible">
            <input type="password" class="form-control" id="${id}" name="${name}" required autocomplete="off">
            <i class="fa fa-lock text-warning"></i>
        </div>
    </div>
</#macro>
<#macro checkBox id label name=id>
	<div class="checkbox">
		<label>
			<input id="${id}" type="checkbox" name="${name}">
			<i class="fa"></i>
			${label}
		</label>
	</div>
</#macro>
